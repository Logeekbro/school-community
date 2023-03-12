package com.db.dbcommunity.search.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.search.document.Article;
import com.db.dbcommunity.search.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SearchController {

    @Resource
    private SearchService searchService;

    /**
     * 根据关键字搜索文章
     */
    @GetMapping("/article")
    public R<MyPage<Article>> searchArticlesByKeyword(@RequestParam String keyword,
                                        @RequestParam Integer current,
                                        @RequestParam Integer size) {
        MyPage<Article> page =  searchService.searchArticle(keyword, current, size);
        return R.success(page);
    }
}
