package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Reply;
import com.db.dbcommunity.article.service.ReplyService;
import com.db.dbcommunity.article.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

/**
* @author bin
* @description 针对表【tb_reply】的数据库操作Service实现
* @createDate 2023-03-07 15:25:06
*/
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply>
    implements ReplyService{

}




