package com.db.dbcommunity.article.controller;

import com.db.dbcommunity.article.model.dto.ArticleCreateDTO;
import com.db.dbcommunity.article.model.dto.ArticleUpdateDTO;
import com.db.dbcommunity.article.model.entity.Article;
import com.db.dbcommunity.article.model.vo.*;
import com.db.dbcommunity.article.service.ArticleService;
import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.MyBeanUtil;
import com.db.dbcommunity.common.util.MyPage;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 创建文章
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public R<SingleKeyVO> createArticle(@Validated @RequestBody ArticleCreateDTO articleCreateDTO) {
        articleCreateDTO.setAuthorId(UserContext.getCurrentUserId());
        Long id = articleService.create(articleCreateDTO);
        SingleKeyVO vo = new SingleKeyVO(id);
        return R.success(vo);
    }

    /**
     * 更新文章
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public R<Void> updateArticle(@Validated @RequestBody ArticleUpdateDTO updateVO) {
        updateVO.setAuthorId(UserContext.getCurrentUserId());

        return articleService.update(updateVO) ? R.success() : R.failed();
    }

    /**
     * 根据用户id获取用户发表的文章列表
     */
    @RequestMapping(value = "/list/userId/{userId}", method = RequestMethod.GET)
    public R<MyPage<UserHomePageArticleInfoVO>> getArticleListByUserId(@PathVariable Long userId,
                                                                       @RequestParam Long current,
                                                                       @RequestParam Short size) {
        MyPage<UserHomePageArticleInfoVO> page = articleService.getArticlePageByUserId(userId, current, size);
        return R.success(page);
    }

    /**
     * 根据文章id删除文章-需要管理员权限
     */
    @RequestMapping(value = "/id/{articleId}", method = RequestMethod.DELETE)
    public R<?> deleteArticleById(@PathVariable("articleId") Long articleId) {
        return articleService.deleteArticleById(articleId, null) ? R.success() : R.failed();
    }

    /**
     * 用户根据id删除自己的文章
     */
    @RequestMapping(value = "/my/id/{articleId}", method = RequestMethod.DELETE)
    public R<?> deleteUserArticleById(@PathVariable("articleId") Long articleId) {
        return articleService.deleteArticleById(articleId, UserContext.getCurrentUserId()) ? R.success() : R.failed();
    }

    /**
     * 点击修改文章时调用此接口获取文章的详细信息
     *
     * @param articleId 文章id
     * @return 文章的详细信息
     */
    @RequestMapping(value = "/my/detail/{articleId}", method = RequestMethod.GET)
    public R<ArticleDetailInfoVO> getArticleDetailById(@PathVariable("articleId") Long articleId) {
        // 用户查看自己的文章详情时可以查看审核中(status=1)以及审核未通过(status=2)的文章
        ArticleDetailInfoVO vo = articleService.getArticleDetailById(articleId, UserContext.getCurrentUserId());
        return vo != null ? R.success(vo) : R.failed(ResultCode.NOT_FOUND);
    }

    /**
     * 文章详情页调用此接口获取文章的详细信息
     *
     * @param articleId 文章id
     * @return 文章的详细信息
     */
    @RequestMapping(value = "/detail/{articleId}", method = RequestMethod.GET)
    public R<ArticleDetailInfoVO> getArticleHomePageDetailById(@PathVariable("articleId") Long articleId) {
        // 此处不传userId只能查询出status=0或3的文章
        ArticleDetailInfoVO vo = articleService.getArticleDetailById(articleId, null);
        return vo != null ? R.success(vo) : R.failed(ResultCode.NOT_FOUND);
    }



    /**
     * openAPI
     * 获取最新文章, 可指定分区
     *
     * @return 文章列表中需要的文章信息
     */
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public R<MyPage<ArticleMainInfoVO>> getLatestArticles(@RequestParam(required = false) Integer sectionId,
                                                          @RequestParam Long current,
                                                          @RequestParam Short size) {
        MyPage<ArticleMainInfoVO> page =
                articleService.getLatestArticleMainInfo(sectionId, current, size);
        return R.success(page);
    }

    /**
     * openAPI
     * 获取热门文章
     *
     * @return 文章列表中需要的文章信息
     */
    //TODO 由于删除了view_count字段，该接口需要重写
    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    public R<MyPage<ArticleMainInfoVO>> getPopularArticles(@RequestParam(required = false) Integer sectionId,
                                                           @RequestParam Long current,
                                                           @RequestParam Short size) {
        MyPage<ArticleMainInfoVO> page =
                articleService.getPopularArticleMainInfo(sectionId, current, size);
        return R.success(page);
    }

    /**
     * openAPI
     * 获取用户发表的文章数量
     *
     */
    @RequestMapping(value = "/count/userId/{authorId}", method = RequestMethod.GET)
    public R<SingleKeyVO> getArticleCountByAuthorId(@PathVariable("authorId") Long authorId) {
        Long count = articleService.getArticleCountByAuthorId(authorId);
        SingleKeyVO vo = new SingleKeyVO(count);
        return R.success(vo);
    }


    /**
     * openAPI
     * 获取置顶文章，可指定分区
     * @param sectionId 分区id，可以为空
     */
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public R<List<ArticleMainInfoVO>> getIndexTopArticle(@RequestParam(value = "sectionId", required = false) Integer sectionId) {
        List<ArticleMainInfoVO> vo = articleService.getTopArticle(sectionId);
        return R.success(vo);
    }

    /**
     * 通过文章id获取文章标题
     */
    @RequestMapping("/open/title/{articleId}")
    public R<SingleKeyVO> getTitleByArticleId(@PathVariable Long articleId) {
        String title = articleService.getTitleByArticleId(articleId);
        SingleKeyVO vo = new SingleKeyVO(title);
        return R.success(vo);
    }

    /**
     * openAPI
     * 获取TopN活跃作者的id和文章数量(活跃的判断依据：发表的文章数量)
     */
    @RequestMapping("/activeAuthors/{n}")
    public R<List<AuthorIdWithArticleCountVO>> getActiveAuthors(@PathVariable Integer n) {
        List<AuthorIdWithArticleCountVO> authors = articleService.getActiveAuthors(n);
        return R.success(authors);
    }




    /**
     * AdminAPI
     * 将文章设为置顶或取消置顶
     */
    @RequestMapping(value = "/topStatus", method = RequestMethod.PUT)
    public R<Void> changeArticleTopStatus(@RequestParam Long articleId) {
        return articleService.changeArticleTopStatus(articleId) ? R.success() : R.failed();
    }
}
