package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.ReviewHistory;
import com.db.dbcommunity.article.service.ReviewHistoryService;
import com.db.dbcommunity.article.mapper.ReviewHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author bin
* @description 针对表【tb_review_history】的数据库操作Service实现
* @createDate 2023-02-18 12:49:50
*/
@Service
public class ReviewHistoryServiceImpl extends ServiceImpl<ReviewHistoryMapper, ReviewHistory>
    implements ReviewHistoryService{

}




