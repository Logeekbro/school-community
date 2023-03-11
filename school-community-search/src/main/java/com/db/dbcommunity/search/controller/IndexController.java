package com.db.dbcommunity.search.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.search.common.ESConstant;
import com.db.dbcommunity.search.document.Article;
import com.db.dbcommunity.search.document.User;
import com.db.dbcommunity.search.service.IndexService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexService;

    /**
     * 将文章索引至ES
     */
    @PostMapping("/article")
    public R<Void> indexArticle(@RequestBody List<Article> articles) {
        return indexService.index(ESConstant.ARTICLE_INDEX_NAME, articles) ? R.success() : R.failed();
    }

    /**
     * 将用户索引至ES
     */
    @PostMapping("/user")
    public R<Void> indexUser(@RequestBody List<User> users) {
        return indexService.index(ESConstant.USER_INDEX_NAME, users) ? R.success() : R.failed();
    }

}
