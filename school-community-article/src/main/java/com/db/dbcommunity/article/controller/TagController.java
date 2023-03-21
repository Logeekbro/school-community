package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.service.TagService;
import com.db.dbcommunity.common.api.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 通过文章id获取该文章的标签名列表
     */
    @GetMapping("/list/articleId/{articleId}")
    public R<List<String>> getTagNameListByArticleId(@PathVariable Long articleId) {
        return R.success(tagService.selectTagNameListByArticleId(articleId));
    }

    /**
     * 根据关键字模糊匹配标签名列表
     */
    @GetMapping("/list/similar")
    public R<List<Object>> getSimilarTagsByKeyword(@RequestParam String keyword) {
        return R.success(tagService.getSimilarTagsByKeyword(keyword));
    }
}
