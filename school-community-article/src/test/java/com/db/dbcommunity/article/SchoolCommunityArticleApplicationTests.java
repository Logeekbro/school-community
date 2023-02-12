package com.db.dbcommunity.article;

import com.db.dbcommunity.article.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class SchoolCommunityArticleApplicationTests {

    @Resource
    private TagService tagService;

    @Test
    void contextLoads() {
    }

    @Test
    void TestTagsCreate() {
        Set<String> set = new HashSet<>();
        set.add("111");
        set.add("222");
        set.add("111");
        tagService.handleArticleTagCreate(1L, set);
    }

}
