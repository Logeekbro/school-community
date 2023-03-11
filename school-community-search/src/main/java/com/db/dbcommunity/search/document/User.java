package com.db.dbcommunity.search.document;

import lombok.Data;

@Data
public class User implements EsDocument{

    private Long userId;

    private String username;

    private String nickName;

    @Override
    public String id() {
        return this.userId.toString();
    }
}
