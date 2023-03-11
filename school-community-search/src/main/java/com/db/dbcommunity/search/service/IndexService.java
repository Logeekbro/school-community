package com.db.dbcommunity.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.db.dbcommunity.search.document.EsDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class IndexService {

    private final Logger logger = LoggerFactory.getLogger(IndexService.class);

    @Resource
    private ElasticsearchClient esClient;

    public boolean index(String indexName, List<? extends EsDocument> documents) {
        try {
            BulkRequest.Builder br = new BulkRequest.Builder();
            for (EsDocument document : documents) {
                br.operations(op -> op
                        .index(idx -> idx
                                .index(indexName)
                                .id(document.id())
                                .document(document)
                        )
                );
            }
            BulkResponse result = esClient.bulk(br.build());
            // Log errors, if any
            if (result.errors()) {
                logger.error("Bulk had errors");
                for (BulkResponseItem item : result.items()) {
                    if (item.error() != null) {
                        logger.error(item.error().reason());
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIndex(String indexName, List<? extends EsDocument> documents) {
        try {
            BulkRequest.Builder br = new BulkRequest.Builder();
            for (EsDocument document : documents) {
                br.operations(op -> op
                        .update(idx -> idx
                                .index(indexName)
                                .id(document.id())
                                .action(o -> o.doc(document))
                        )
                );
            }
            BulkResponse result = esClient.bulk(br.build());
            // Log errors, if any
            if (result.errors()) {
                logger.error("Bulk had errors");
                for (BulkResponseItem item : result.items()) {
                    if (item.error() != null) {
                        logger.error(item.error().reason());
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIndex(String indexName, List<String> ids) {
        try {
            esClient.deleteByQuery(builder -> builder.index(indexName).query(q -> q.ids(b -> b.values(ids))));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
