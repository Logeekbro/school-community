package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentInListVO {

    private Long commentId;

    private Long userId;

    private String content;

    private Date createTime;
}
