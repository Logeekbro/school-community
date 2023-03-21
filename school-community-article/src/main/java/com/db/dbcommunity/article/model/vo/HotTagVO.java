package com.db.dbcommunity.article.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotTagVO implements Serializable {

    private String tagName;
    private Long articleCount;
}
