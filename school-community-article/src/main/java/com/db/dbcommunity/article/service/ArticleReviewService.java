package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.vo.ArticleDetailInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleMainInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
import com.db.dbcommunity.article.model.vo.UserHomePageArticleInfoVO;

import java.util.List;

public interface ArticleReviewService {

    /**
     * 根据用户id获取待审核的文章列表
     * @param userId 用户id，可以为空
     */
    List<ArticleDetailInfoVO> getNeedReviewArticleListById(Long userId);

    /**
     * 更新文章审核状态(通过/不通过)
     */
    boolean updateReviewStatus(Long articleId, ArticleReviewResultVO vo);

    /**
     * 根据用户id获取审核不通过的文章
     */
    List<ArticleMainInfoVO> getUnPassReviewArticleList(Long currentUserId);
}
