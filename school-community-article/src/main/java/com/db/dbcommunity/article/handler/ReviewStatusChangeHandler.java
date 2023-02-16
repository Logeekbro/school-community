package com.db.dbcommunity.article.handler;

import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
import com.db.dbcommunity.article.thread.ServiceContext;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.stereotype.Component;


@Component
public class ReviewStatusChangeHandler implements IDataChangeHandler {

    @Override
    public DataChangeType[] handleType() {
        return new DataChangeType[]{DataChangeType.ARTICLE_PASS_REVIEW, DataChangeType.ARTICLE_NOT_PASS_REVIEW};
    }

    @Override
    public Runnable runnable() {
        return () -> {
            ArticleReviewResultVO resultVO = ServiceContext.getReviewResultVo();
            // 审核员的id
            Long reviewUserId = UserContext.getCurrentUserId();
            System.out.println("resultVO: " + resultVO);
            Long articleId = ServiceContext.getArticleId();
            System.out.println("articleId: " + articleId);
        };
    }
}
