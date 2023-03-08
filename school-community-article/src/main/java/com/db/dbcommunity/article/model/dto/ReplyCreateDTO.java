package com.db.dbcommunity.article.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReplyCreateDTO {

    @NotNull(message = "回复的评论id不能为空")
    private Long commentId;

    private Long target;
    @NotEmpty(message = "回复内容不能为空")
    private String content;
}
