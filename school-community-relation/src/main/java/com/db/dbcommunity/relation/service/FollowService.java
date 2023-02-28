package com.db.dbcommunity.relation.service;

public interface FollowService {

    /**
     * 关注某个用户
     * @param currentUserId 关注者id
     * @param beFollowUserId 被关注者id
     */
    boolean addFollow(Long currentUserId, Long beFollowUserId);

    /**
     * 取消关注某个用户
     * @param currentUserId 取关者id
     * @param beFollowUserId 要取关的用户id
     */
    boolean deleteFollow(Long currentUserId, Long beFollowUserId);
}
