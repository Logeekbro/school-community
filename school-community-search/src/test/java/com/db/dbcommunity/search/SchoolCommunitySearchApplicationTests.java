package com.db.dbcommunity.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.ElasticsearchCatClient;
import co.elastic.clients.elasticsearch.cat.HealthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class SchoolCommunitySearchApplicationTests {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Test
    void contextLoads() throws IOException {
        System.out.println(elasticsearchClient);
        ElasticsearchCatClient cat = elasticsearchClient.cat();
        HealthResponse health = cat.health();
        System.out.println(health);
    }

}
