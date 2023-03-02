package com.db.dbcommunity.score.service;

public interface LikeService {

    /**
     * 改变点赞状态
     * @param isLike true-点赞，false-取消点赞
     */
    boolean changeLike(String type, Long id, Long userId, boolean isLike);
}
