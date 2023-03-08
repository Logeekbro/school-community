package com.db.dbcommunity.article.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentCreateDTO {

    private Long articleId;

    @NotEmpty(message = "评论内容不能为空")
    @Size(min = 1, message = "评论内容不能为空")
    private String content;
}
