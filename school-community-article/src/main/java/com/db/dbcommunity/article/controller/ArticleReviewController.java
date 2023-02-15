package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.vo.ArticleMainInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
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
     * 获取所有待审核文章
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
     * AdminAPI
     * 更新文章审核状态
     */
//    @RequestMapping(value = "/admin/review/id/{articleId}", method = RequestMethod.PUT)
//    @PreAuthorize("hasAuthority('article.review')")
//    public R<?> passArticle(@PathVariable Long articleId,
//                            @RequestParam(defaultValue = "") String description,
//                            @NotNull(message = "请选择是否通过审核") Boolean isPass) {
//        Boolean result = articleService.
//                changeArticleReviewStatus(articleId, UserContext.getCurrentUserId(), description, isPass);
//        if (result) return R.success();
//        else return R.failed();
//    }

    /**
     * 根据用户id获取该用户待审核的文章列表
     */
//    @RequestMapping(value = "/needReviewList", method = RequestMethod.GET)
//    public R<List<? extends ArticleVO>> getUserNeedReviewArticleList() {
//        List<? extends ArticleVO> list = articleService.getNeedReviewArticleList(UserContext.getCurrentUserId());
//        return R.success(list);
//    }

    /**
     * 根据用户id获取该用户审核未通过的文章列表
     */
//    @RequestMapping(value = "/unPassReviewList", method = RequestMethod.GET)
//    public R<List<? extends ArticleVO>> getUnPassReviewList() {
//        List<? extends ArticleVO> list = articleService.getUnPassReviewArticleList(UserContext.getCurrentUserId());
//        return R.success(list);
//    }
}
