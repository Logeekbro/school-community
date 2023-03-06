package com.db.dbcommunity.visit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.visit.model.entity.History;
import com.db.dbcommunity.visit.model.vo.HistoryWithDateVO;
import com.db.dbcommunity.visit.model.vo.UserHistoryVO;
import com.db.dbcommunity.visit.service.HistoryService;
import com.db.dbcommunity.visit.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_history】的数据库操作Service实现
* @createDate 2023-03-05 10:29:06
*/
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
    implements HistoryService {

    @Override
    public boolean add(Long currentUserId, Long articleId) {
        History history = new History();
        history.setUserId(currentUserId);
        history.setArticleId(articleId);
        return this.baseMapper.insert(history) > 0;
    }

    @Override
    public HistoryWithDateVO getUserHistoryByDate(Long currentUserId, String targetDate) {
        List<UserHistoryVO> vos = this.baseMapper.selectUserHistoryByDate(currentUserId, targetDate);
        HistoryWithDateVO vo = new HistoryWithDateVO();
        vo.setTargetDate(targetDate);
        vo.setRecords(vos);
        vo.setBeforeDate(this.baseMapper.selectBeforeDate(currentUserId, targetDate));
        return vo;
    }

    @Override
    public boolean deleteHistoryById(Long currentUserId, Long historyId) {
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(History::getUserId, currentUserId);
        queryWrapper.eq(History::getHistoryId, historyId);
        return this.baseMapper.delete(queryWrapper) > 0;
    }

    @Override
    public boolean deleteAllHistoryByUserId(Long currentUserId) {
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(History::getUserId, currentUserId);
        return this.baseMapper.delete(queryWrapper) > 0;
    }
}




