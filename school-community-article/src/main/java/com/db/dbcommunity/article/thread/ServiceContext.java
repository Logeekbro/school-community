package com.db.dbcommunity.article.thread;

import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;

public class ServiceContext {

    private static final ThreadLocal<Long> articleId = new ThreadLocal<>();

    private static final ThreadLocal<ArticleReviewResultVO> reviewResultVo = new ThreadLocal<>();

    public static void setArticleId(Long id) {
        articleId.set(id);
    }

    public static Long getArticleId() {
        return articleId.get();
    }

    public static void setReviewResultVo(ArticleReviewResultVO vo) {
        reviewResultVo.set(vo);
    }

    public static ArticleReviewResultVO getReviewResultVo() {
        return reviewResultVo.get();
    }

}
