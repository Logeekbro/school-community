package com.db.dbcommunity.visit.service;

import com.db.dbcommunity.visit.model.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.visit.model.vo.HistoryWithDateVO;

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

    /**
     * 根据日期获取用户历史记录
     */
    HistoryWithDateVO getUserHistoryByDate(Long currentUserId, String targetDate);

    /**
     * 根据id删除历史记录
     */
    boolean deleteHistoryById(Long currentUserId, Long historyId);

    /**
     * 删除用户的所有历史记录
     */
    int deleteAllHistoryByUserId(Long currentUserId);
}
