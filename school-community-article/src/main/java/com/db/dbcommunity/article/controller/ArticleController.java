package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.vo.ArticleCreateVO;
import com.db.dbcommunity.article.model.vo.ArticleUpdateVO;
import com.db.dbcommunity.article.service.ArticleService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 创建文章
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public R<SingleKeyVO> createArticle(@Validated @RequestBody ArticleCreateVO articleVO) {
        articleVO.setAuthorId(UserContext.getCurrentUserId());
        Long id = articleService.create(articleVO);
        SingleKeyVO vo = new SingleKeyVO(id);
        return R.success(vo);
    }

    /**
     * 更新文章
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public R<Void> updateArticle(@Validated @RequestBody ArticleUpdateVO updateVO) {
        updateVO.setAuthorId(UserContext.getCurrentUserId());
        return articleService.update(updateVO) ? R.success() : R.failed();
    }

    /**
     * 获取用户主页文章列表
     * SQL Override Complete
     */
//    @RequestMapping(value = "/open/user/list", method = RequestMethod.GET)
//    public R<MySimplePage<UserArticleListVO>> getArticleListByUserId(@RequestParam Integer userId,
//                                                                     @RequestParam Long current,
//                                                                     @RequestParam Long size) {
//        MySimplePage<UserArticleListVO> page = articleService.getArticlePageByUserId(userId, current, size);
//        return R.success(page);
//    }

    /**
     * 删除文章
     * SQL Override Complete
     */
//    @RequestMapping(value = "/{articleId}", method = RequestMethod.DELETE)
//    public R<?> deleteArticleById(@PathVariable("articleId") Long articleId) {
//        if (articleService.deleteArticleById(articleId, UserContext.getCurrentUserId())) {
//            return R.success();
//        } else {
//            return R.failed();
//        }
//    }

    /**
     * openAPI
     * 获取文章的详细信息（包括作者id，标签，文章的内容、标题...等，详见返回值->ArticleInfoWithTagAndAuthorIdVO）
     * SQL Override Complete
     *
     * @param articleId 文章id
     * @param isReEdit  是否是重新编辑的文章
     * @return
     */
//    @RequestMapping(value = "/open/detail/{articleId}", method = RequestMethod.GET)
//    public R<ArticleInfoVO> getArticleDetailById(@PathVariable("articleId") Long articleId,
//                                                 @RequestParam(defaultValue = "false") Boolean isReEdit) {
//        ArticleInfoVO vo = (ArticleInfoVO) articleService.getArticleDetailByArticleId(articleId, isReEdit);
//        // 当该文章是重新编辑的文章时，内容只有作者本人能查看
//        if (isReEdit && !vo.getAuthorId().equals(UserContext.getCurrentUserId()))
//            return R.failed(ResultCode.NOT_FOUND);
//        return R.success(vo);
//    }

    /**
     * openAPI
     * 获取最新文章
     * SQL Override Complete
     *
     * @return
     */
//    @RequestMapping(value = "/open/latest", method = RequestMethod.GET)
//    public R<MySimplePage<ArticleAndAuthorInfoVO>> getLatestArticles(@Param("current") Long current,
//                                                                     @Param("size") Long size) {
//        MySimplePage<ArticleAndAuthorInfoVO> page =
//                articleService.getLatestArticleDetails(current, size);
//        return R.success(page);
//    }

    /**
     * openAPI
     * 获取热门文章
     * SQL Override Complete
     *
     * @param current
     * @param size
     * @return
     */
//    @RequestMapping(value = "/open/popular", method = RequestMethod.GET)
//    public R<MySimplePage<ArticleAndAuthorInfoVO>> getPopularArticles(@Param("current") Long current,
//                                                                      @Param("size") Long size) {
//        MySimplePage<ArticleAndAuthorInfoVO> page =
//                articleService.getPopularArticleDetails(current, size);
//        return R.success(page);
//    }

    /**
     * openAPI
     * 获取用户发表的文章数量
     * SQL Override Complete
     *
     * @param authorId
     * @return
     */
//    @RequestMapping(value = "/open/count/{authorId}", method = RequestMethod.GET)
//    public R<SingleKeyVO> getArticleCountByAuthorId(@PathVariable("authorId") int authorId) {
//        Long count = articleService.getArticleCountByAuthorId(authorId);
//        SingleKeyVO vo = new SingleKeyVO(count);
//        return R.success(vo);
//    }


    /**
     * openAPI
     * 获取置顶文章
     */
//    @RequestMapping(value = "/open/index/top", method = RequestMethod.GET)
//    public R<List<ArticleAndAuthorInfoVO>> getIndexTopArticle() {
//        List<ArticleAndAuthorInfoVO> vo = articleService.getIndexTopArticle();
//        return R.success(vo);
//    }

    /**
     * openAPI
     * 通过文章id获取文章标题
     */
//    @RequestMapping("/open/title/{articleId}")
//    public R<SingleKeyVO> getTitleByArticleId(@PathVariable Long articleId) {
//        String title = articleService.getTitleByArticleId(articleId);
//        SingleKeyVO vo = new SingleKeyVO(title);
//        return R.success(vo);
//    }

    /**
     * openAPI
     * 获取TopN活跃作者的id及其发表的文章数量
     */
//    @RequestMapping("/open/mostActiveAuthors/{n}")
//    public R<List<ArticleActiveAuthorVO>> getActiveAuthors(@PathVariable Integer n) {
//        List<ArticleActiveAuthorVO> authors = articleService.getActiveAuthors(n);
//        return R.success(authors);
//    }

    /**
     * AdminAPI
     * 直接根据id删除文章
     */
//    @RequestMapping(value = "/admin/id", method = RequestMethod.DELETE)
//    @PreAuthorize("hasAuthority('article.delete')")
//    public R<Boolean> deleteArticleByArticleId(@RequestParam Long articleId) {
//        boolean result = articleService.removeById(articleId);
//        if (result) return R.success();
//        return R.failed("删除失败");
//    }

    /**
     * AdminAPI
     * 获取所有待审核的文章
     */
//    @RequestMapping("/admin/needReviewList")
//    @PreAuthorize("hasAuthority('article.review')")
//    public R<List<? extends ArticleVO>> getNeedReviewArticleList() {
//        return R.success(articleService.getNeedReviewArticleList(null));
//    }

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

    /**
     * AdminAPI
     * 将文章设为置顶或取消置顶
     */
//    @RequestMapping(value = "/admin/topStatus", method = RequestMethod.PUT)
//    public R<SingleKeyVO> changeArticleTopStatus(@RequestParam Long articleId) {
//        Boolean result = articleService.changeArticleTopStatus(articleId);
//        SingleKeyVO vo = new SingleKeyVO(result);
//        return R.success(vo);
//    }
}
