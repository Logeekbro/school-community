package com.db.dbcommunity.search.document;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Article implements EsDocument, Serializable {

    private Long articleId;

    private Long authorId;

    private String title;

    private String summary;

    private String mainPic;

    private Integer status;

    private Long viewCount = 0L;

    private Long likeCount = 0L;

    private Date createTime;

    @Override
    public String id() {
        return this.articleId.toString();
    }
}
