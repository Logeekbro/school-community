package com.db.dbcommunity.search.document;

import lombok.Data;

import java.util.Date;

@Data
public class User implements EsDocument{

    private Long userId;

    private String username;

    private String nickName;

    private String avatar;

    private Long fansCount;

    private Date createTime;

    @Override
    public String id() {
        return this.userId.toString();
    }
}
