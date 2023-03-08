package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyInListVO {

    private Long replyId;

    private Long userId;

    private Long target;

    private String content;

    private Date createTime;
}
