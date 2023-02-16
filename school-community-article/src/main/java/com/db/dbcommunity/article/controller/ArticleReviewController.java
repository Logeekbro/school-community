package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.vo.ArticleMainInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
import com.db.dbcommunity.article.model.vo.UserHomePageArticleInfoVO;
import com.db.dbcommunity.article.service.ArticleReviewService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ArticleReviewController {

    @Resource
    private ArticleReviewService articleReviewService;

    /**
     * 用户获取自己的待审核文章
     */
    @GetMapping("/list/my")
    public R<List<ArticleMainInfoVO>> getUserNeedReviewArticleList() {
        List<ArticleMainInfoVO> list = articleReviewService.getNeedReviewArticleListById(UserContext.getCurrentUserId());
        return R.success(list);
    }

    /**
     * AdminAPI
     * 获取所有待审核文章列表
     */
    @GetMapping("/list")
    public R<List<ArticleMainInfoVO>> getAllNeedReviewArticleList() {
        List<ArticleMainInfoVO> list = articleReviewService.getNeedReviewArticleListById(null);
        return R.success(list);
    }

    /**
     * AdminAPI
     * 更新文章审核状态
     */
    @RequestMapping(value = "/result/articleId/{articleId}", method = RequestMethod.PUT)
    public R<Void> passArticle(@PathVariable Long articleId, @Validated @RequestBody ArticleReviewResultVO vo) {
        return articleReviewService.updateReviewStatus(articleId, vo) ? R.success() : R.failed();
    }


    /**
     * 用户获取自己审核未通过的文章列表
     */
    @RequestMapping(value = "/unPass", method = RequestMethod.GET)
    public R<List<ArticleMainInfoVO>> getUnPassReviewList() {
        List<ArticleMainInfoVO> list = articleReviewService.getUnPassReviewArticleList(UserContext.getCurrentUserId());
        return R.success(list);
    }
}
