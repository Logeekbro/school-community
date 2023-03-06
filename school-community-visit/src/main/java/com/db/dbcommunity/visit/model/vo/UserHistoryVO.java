package com.db.dbcommunity.visit.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserHistoryVO implements Serializable {

    private Long historyId;

    private Long articleId;

    private Date createTime;
}
