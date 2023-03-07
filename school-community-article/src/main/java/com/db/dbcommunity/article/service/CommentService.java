package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.article.model.vo.CommentCreateVO;

/**
* @author bin
* @description 针对表【tb_comment】的数据库操作Service
* @createDate 2023-03-07 15:25:06
*/
public interface CommentService extends IService<Comment> {

    /**
     * 添加评论
     */
    boolean createComment(Long currentUserId, CommentCreateVO vo);

    /**
     * 根据id删除评论
     */
    boolean deleteById(Long currentUserId, Long commentId);
}
