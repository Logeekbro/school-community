package com.db.dbcommunity.search.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.search.document.Article;
import com.db.dbcommunity.search.service.IndexService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexService;

    /**
     * 将文章索引至ES
     */
    @PostMapping("/article")
    public R<Void> indexArticle(@RequestBody Article[] articles) {
        return indexService.indexArticle(articles) ? R.success() : R.failed();
    }

}
