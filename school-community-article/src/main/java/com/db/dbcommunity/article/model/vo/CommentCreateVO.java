package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentCreateVO {

    private Long articleId;

    @NotEmpty(message = "评论内容不能为空")
    @Size(min = 1, message = "评论内容不能为空")
    private String content;
}
