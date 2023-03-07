package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Comment;
import com.db.dbcommunity.article.service.CommentService;
import com.db.dbcommunity.article.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author bin
* @description 针对表【tb_comment】的数据库操作Service实现
* @createDate 2023-03-07 15:25:06
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




