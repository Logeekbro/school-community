package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.vo.HotTagVO;
import com.db.dbcommunity.article.service.TagService;
import com.db.dbcommunity.common.api.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public R<List<Map<String, Object>>> getSimilarTagsByKeyword(@RequestParam String keyword) {
        return R.success(tagService.getSimilarTagsByKeyword(keyword));
    }

    /**
     * 获取热门标签及其对应的文章数量
     */
    @GetMapping("/list/hot/{n}")
    public R<List<HotTagVO>> getHotTags(@PathVariable Integer n) {
        return R.success(tagService.getHotTags(n));
    }
}
