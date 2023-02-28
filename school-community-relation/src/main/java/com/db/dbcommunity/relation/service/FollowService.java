package com.db.dbcommunity.relation.service;

public interface FollowService {

    /**
     * 关注/取消关注 某个用户
     * @param currentUserId 关注者id
     * @param beFollowUserId 被关注者id
     * @param isFollow true-关注 false-取消关注
     */
    boolean changeFollow(Long currentUserId, Long beFollowUserId, boolean isFollow);

    /**
     * 检查一个用户是否关注了另一个用户
     * @param currentUserId 一个用户id
     * @param beFollowUserId 另一个用户id
     * @return true-已关注 false-未关注
     */
    boolean isFollow(Long currentUserId, Long beFollowUserId);
}
