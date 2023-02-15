package com.db.dbcommunity.article.util;

import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
import com.db.dbcommunity.article.thread.ServiceContext;

import java.util.function.Supplier;

public class MethodUtil {

    /**
     * 调用会改变数据的方法时的统一包装方法，以便于添加 数据改变时 的统一操作，如：更新缓存、发送消息等
     * @param type 调用的方法标识
     * @param supplier 调用的方法
     * @return 是否执行成功
     */
    public static boolean dataChangeCall(DataChangeType type, Supplier<Boolean> supplier) {
        // 前置操作...

        // 真正的方法执行
        Boolean result = supplier.get();

        // 后置操作...
        switch (type) {
            case ARTICLE_PASS_REVIEW:
            case ARTICLE_NOT_PASS_REVIEW:
                ArticleReviewResultVO resultVO = ServiceContext.getReviewResultVo();
                System.out.println("resultVO: " + resultVO);
                Long articleId = ServiceContext.getArticleId();
                System.out.println("articleId: " + articleId);
                System.out.println("tid:" + Thread.currentThread().getId());
                // 1.保存审核记录

                // 2.发送通知给用户
                break;
        }
        return result;
    }
}
