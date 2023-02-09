package com.db.dbcommunity.message.document;

import com.db.dbcommunity.message.enums.DisplayName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReviewResultNotion extends Notion implements Serializable {

    /**
     * 审核者的用户id
     */
    private Long reviewerId;

    /**
     * 被审核文章作者的用户id
     */
    private Long targetId;

    /**
     * 被审核文章id
     */
    private String articleId;

    /**
     * 是否通过审核
     */
    private Boolean isPass;

    /**
     * 审核结果的描述信息
     */
    private String description;

    /**
     * 用于标识通知的类别，如：点赞通知、文章审核结果通知等...
     */
    private DisplayName displayName;

    public ReviewResultNotion(Long reviewerId, String articleId, Boolean isPass, String description) {
        this.reviewerId = reviewerId;
        this.articleId = articleId;
        this.isPass = isPass;
        this.description = description;
        this.displayName = DisplayName.REVIEW_RESULT_NOTION;
    }
}
