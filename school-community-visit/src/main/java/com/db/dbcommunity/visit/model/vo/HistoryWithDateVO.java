package com.db.dbcommunity.visit.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HistoryWithDateVO implements Serializable {

    private List<UserHistoryVO> records;

    /**
     * 前一个有历史记录的日期
     */
    private String beforeDate;

    /**
     * 当前历史记录列表的日期
     */
    private String targetDate;


}
