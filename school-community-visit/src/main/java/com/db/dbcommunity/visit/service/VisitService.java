package com.db.dbcommunity.visit.service;

public interface VisitService {

    /**
     * 用户浏览文章后的操作(增加浏览量等)
     * @param currentUserId 浏览文章的用户id
     * @param articleId 被浏览的文章id
     */
    boolean handleVisit(Long currentUserId, Long articleId);

    /**
     * 根据文章id获取文章浏览量
     */
    Long getVisitCountByArticleId(Long articleId);
}
