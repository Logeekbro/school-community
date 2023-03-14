package com.db.dbcommunity.search.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.search.common.ESConstant;
import com.db.dbcommunity.search.document.Article;
import com.db.dbcommunity.search.document.ArticleUpdate;
import com.db.dbcommunity.search.document.User;
import com.db.dbcommunity.search.document.UserUpdate;
import com.db.dbcommunity.search.service.IndexService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据id列表将文章数据从ES中删除
     */
    @DeleteMapping("/article")
    public R<Void> deleteArticleIndex(@RequestBody List<String> ids) {
        return indexService.deleteIndex(ESConstant.ARTICLE_INDEX_NAME, ids) ? R.success() : R.failed();
    }

    /**
     * 根据id列表将用户数据从ES中删除
     */
    @DeleteMapping("/user")
    public R<Void> deleteUserIndex(@RequestBody List<String> ids) {
        return indexService.deleteIndex(ESConstant.USER_INDEX_NAME, ids) ? R.success() : R.failed();
    }

    /**
     * 更新文章索引
     */
    @PutMapping("/article")
    public R<Void> updateArticleIndex(@RequestBody List<ArticleUpdate> articles) {
        return indexService.updateIndex(ESConstant.ARTICLE_INDEX_NAME, articles) ? R.success() : R.failed();
    }

    /**
     * 更新用户索引
     */
    @PutMapping("/user")
    public R<Void> updateUserIndex(@RequestBody List<UserUpdate> users) {
        return indexService.updateIndex(ESConstant.USER_INDEX_NAME, users) ? R.success() : R.failed();
    }

}
