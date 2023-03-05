package com.db.dbcommunity.visit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.visit.model.entity.History;
import com.db.dbcommunity.visit.service.HistoryService;
import com.db.dbcommunity.visit.mapper.HistoryMapper;
import org.springframework.stereotype.Service;

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
}




