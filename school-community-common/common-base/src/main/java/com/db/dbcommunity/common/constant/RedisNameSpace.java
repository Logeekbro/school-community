package com.db.dbcommunity.common.constant;

public interface RedisNameSpace {

    // 用户id集合
    String USER_ID_SET_KEY = "userIds";
    // 文章id集合
    String ARTICLE_ID_SET_KEY = "articleIds";
    // 登录时的验证码前缀
    String LOGIN_VERIFY_CODE_PREFIX = "user:login:verify:";
    // 验证成功的授权码前缀
    String LOGIN_ACCESS_TOKEN_PREFIX = "user:login:accessToken:";
    // 评论数前缀
    String COMMENT_COUNT_PREFIX = "comment:count:";
    // 浏览量前缀
    String VIEW_COUNT_PREFIX = "view:count:";
    // 组成验证码的汉字集合
    String VERIFY_WORD_SET = "verifyWord:set:";
    // 用户有效的jti集合
    String JTI_PREFIX = "jti:";
    // 关注列表前缀
    String FOLLOW_PREFIX = "follow:";
    // 粉丝列表前缀
    String FANS_PREFIX = "fans:";
    // 文章点赞状态前缀
    String ARTICLE_LIKE_STATUS_PREFIX = "article:like:";
    // 文章点赞数前缀
    String ARTICLE_LIKE_COUNT = "article:likeCount:";
    // 文章浏览状态前缀
    String ARTICLE_VISIT_STATUS = "article:visit:";
    // 文章浏览量前缀
    String ARTICLE_VISIT_COUNT = "article:visitCount:";
}
