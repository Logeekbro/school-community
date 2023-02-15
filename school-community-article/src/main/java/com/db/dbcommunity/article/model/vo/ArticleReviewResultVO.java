package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 审核结果信息
 */
@Data
public class ArticleReviewResultVO {

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 是否审核通过
     */
    @NotNull(message = "请选择是否通过审核")
    private Boolean isPass;

    /**
     * 审核 通过/不通过 的描述
     */
    private String desc = "";
}
