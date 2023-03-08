package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Comment;
import com.db.dbcommunity.article.model.vo.CommentCreateVO;
import com.db.dbcommunity.article.model.vo.CommentInListVO;
import com.db.dbcommunity.article.service.CommentService;
import com.db.dbcommunity.article.mapper.CommentMapper;
import com.db.dbcommunity.common.util.MyPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_comment】的数据库操作Service实现
* @createDate 2023-03-07 15:25:06
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Override
    public boolean createComment(Long currentUserId, CommentCreateVO vo) {
        Comment comment = new Comment();
        comment.setArticleId(vo.getArticleId());
        comment.setContent(vo.getContent());
        comment.setUserId(currentUserId);
        return this.baseMapper.insert(comment) > 0;
    }

    @Override
    public boolean deleteById(Long currentUserId, Long commentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCommentId, commentId);
        queryWrapper.eq(Comment::getUserId, currentUserId);
        return this.baseMapper.delete(queryWrapper) > 0;
    }

    @Override
    public MyPage<CommentInListVO> getCommentListByArticleId(Long articleId, Long current, Short size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        Long total = this.baseMapper.selectCount(queryWrapper);
        MyPage<CommentInListVO> page = new MyPage<>(current, size, total);
        List<CommentInListVO> list = this.baseMapper.selectCommentList(articleId, page.offset(), page.getSize());
        page.setRecords(list);
        return page;
    }
}




