package com.db.dbcommunity.article.handler;

import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.model.entity.ReviewHistory;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
import com.db.dbcommunity.article.service.ReviewHistoryService;
import com.db.dbcommunity.article.thread.MyThreadPoolExecutor;
import com.db.dbcommunity.article.thread.ServiceContext;
import com.db.dbcommunity.common.IDataChangeHandler;
import com.db.dbcommunity.common.util.UserContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ReviewStatusChangeHandler implements IDataChangeHandler {

    @Resource
    private ReviewHistoryService reviewHistoryService;

    @Override
    public String[] handleType() {
        return new String[]{DataChangeType.ARTICLE_PASS_REVIEW.getDesc(),
                DataChangeType.ARTICLE_NOT_PASS_REVIEW.getDesc()};
    }

    @Override
    public Runnable runnable() {
        return () -> {
            ArticleReviewResultVO resultVO = ServiceContext.getReviewResultVo();
            // 审核员的id
            Long reviewUserId = UserContext.getCurrentUserId();
            Long articleId = ServiceContext.getArticleId();
            // 保存审核记录
            MyThreadPoolExecutor.run(() -> {
                try{
                    ReviewHistory reviewHistory = new ReviewHistory();
                    reviewHistory.setArticleId(articleId);
                    reviewHistory.setIsPass(resultVO.getIsPass());
                    reviewHistory.setDescription(resultVO.getDesc());
                    reviewHistory.setUserId(reviewUserId);
                    reviewHistoryService.save(reviewHistory);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        };
    }
}
