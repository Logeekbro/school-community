package com.db.dbcommunity.visit.service;

import com.db.dbcommunity.visit.model.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author bin
* @description 针对表【tb_history】的数据库操作Service
* @createDate 2023-03-05 10:29:06
*/
public interface HistoryService extends IService<History> {

    /**
     * 保存浏览记录
     */
    boolean add(Long currentUserId, Long articleId);
}
