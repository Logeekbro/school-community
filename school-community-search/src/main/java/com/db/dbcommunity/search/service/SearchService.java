package com.db.dbcommunity.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.SpanTermQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.search.common.ESConstant;
import com.db.dbcommunity.search.document.Article;
import com.db.dbcommunity.search.document.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class SearchService {

    @Resource
    private ElasticsearchClient client;

    public MyPage<Article> searchArticle(String keyword, Integer current, Integer size) {
        MyPage<Article> page = new MyPage<>(current.longValue(), size.shortValue());
        try {
            SearchResponse<Article> response = client.search(s -> s
                            .index(ESConstant.ARTICLE_INDEX_NAME)
                            .query(q -> q
                                    .match(m -> m
                                            .field("title")
                                            .query(keyword)))
                            .highlight(h -> h
                                    .fields("title", j -> j
                                            .preTags("<b style='color: #009ad6'>")
                                            .postTags("</b>")))
                            .from(page.offset().intValue())
                            .size(page.getSize().intValue()),
                    Article.class
            );
            HitsMetadata<Article> hits = response.hits();
            TotalHits totalHits = hits.total();
            long total = totalHits != null ? totalHits.value() : 0L;
            page.setTotal(total);
            List<Article> records = new LinkedList<>();
            for (Hit<Article> hit : hits.hits()) {
                Article source = hit.source();
                if (source != null) {
                    source.setTitle(hit.highlight().get("title").get(0));
                }
                records.add(source);
            }
            page.setRecords(records);
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MyPage<User> searchUser(String keyword, Integer current, Integer size) {
        MyPage<User> page = new MyPage<>(current.longValue(), size.shortValue());
        Query byUsername = MatchQuery.of(m -> m
                .field("username")
                .query(keyword))._toQuery();
        Query byNickName = MatchQuery.of(m -> m
                .field("nickName")
                .query(keyword))._toQuery();
        try {
            SearchResponse<User> response = client.search(s -> s
                            .index(ESConstant.USER_INDEX_NAME)
                            .query(q -> q
                                    .bool(b -> b
                                            .must(m -> m
                                                    .bool(bo -> {
                                                                bo.should(byUsername).should(byNickName);
                                                                if (keyword.matches("\\d+")) {
                                                                    bo.should(sh -> sh
                                                                            .match(ma -> ma
                                                                                    .field("userId")
                                                                                    .query(keyword)));
                                                                }
                                                                return bo;
                                                            }
                                                    ))))
                            .highlight(h -> h
                                    .fields("nickName", j -> j
                                            .preTags("<b style='color: #009ad6'>")
                                            .postTags("</b>")))
                            .from(page.offset().intValue())
                            .size(page.getSize().intValue()),
                    User.class
            );
            HitsMetadata<User> hits = response.hits();
            TotalHits totalHits = hits.total();
            long total = totalHits != null ? totalHits.value() : 0L;
            page.setTotal(total);
            List<User> records = new LinkedList<>();
            for (Hit<User> hit : hits.hits()) {
                User source = hit.source();
                if (source != null && hit.highlight().get("nickName") != null) {
                    source.setNickName(hit.highlight().get("nickName").get(0));
                }
                records.add(source);
            }
            page.setRecords(records);
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
