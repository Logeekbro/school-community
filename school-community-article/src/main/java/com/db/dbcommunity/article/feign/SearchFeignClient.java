package com.db.dbcommunity.article.feign;

import com.db.dbcommunity.article.model.entity.Article;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.SearchConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("school-community-search")
public interface SearchFeignClient {

    /**
     * 将文章索引至ES
     */
    @PostMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> indexArticle(@RequestBody List<Article> articles);

    /**
     * 将文章索引至ES
     */
    @PostMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> indexArticle(@RequestBody Article[] articles);

    /**
     * 根据id列表将文章数据从ES中删除
     */
    @DeleteMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> deleteArticleIndex(@RequestBody List<Long> ids);

    /**
     * 根据id列表将文章数据从ES中删除
     */
    @DeleteMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> deleteArticleIndex(@RequestBody Long[] ids);

    /**
     * 更新文章索引
     */
    @PutMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> updateArticleIndex(@RequestBody List<Article> articles);

    /**
     * 更新文章索引
     */
    @PutMapping(SearchConstant.URI_PREFIX + "/index/article")
    R<Void> updateArticleIndex(@RequestBody Article[] articles);
}
