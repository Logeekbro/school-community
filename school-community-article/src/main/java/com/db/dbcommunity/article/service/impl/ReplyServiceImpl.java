package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.model.dto.ReplyCreateDTO;
import com.db.dbcommunity.article.model.entity.Reply;
import com.db.dbcommunity.article.model.vo.ReplyInListVO;
import com.db.dbcommunity.article.service.ReplyService;
import com.db.dbcommunity.article.mapper.ReplyMapper;
import com.db.dbcommunity.common.util.MyPage;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.db.dbcommunity.article.util.MethodUtil.dataChangeCall;

/**
* @author bin
* @description 针对表【tb_reply】的数据库操作Service实现
* @createDate 2023-03-07 15:25:06
*/
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply>
    implements ReplyService{

    @Override
    public boolean saveReply(Long currentUserId, ReplyCreateDTO dto) {
        Reply reply = new Reply();
        reply.setUserId(currentUserId);
        reply.setCommentId(dto.getCommentId());
        reply.setContent(dto.getContent());
        reply.setTarget(dto.getTarget());
        return dataChangeCall(DataChangeType.ADD_REPLY, () -> this.baseMapper.insert(reply) > 0);
    }

    @Override
    public boolean deleteById(Long currentUserId, Long replyId) {
        LambdaQueryWrapper<Reply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Reply::getUserId, currentUserId);
        queryWrapper.eq(Reply::getReplyId, replyId);
        return this.baseMapper.delete(queryWrapper) > 0;
    }

    @Override
    public MyPage<ReplyInListVO> getReplyListByCommentId(Long commentId, Long current, Short size) {
        LambdaQueryWrapper<Reply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Reply::getCommentId, commentId);
        Long total = this.baseMapper.selectCount(queryWrapper);
        MyPage<ReplyInListVO> page = new MyPage<>(current, size, total);
        List<ReplyInListVO> list = this.baseMapper.selectReplyListByCommentId(commentId, page.offset(), page.getSize());
        page.setRecords(list);
        return page;
    }

    @Override
    public Long getReplyCountByCommentId(Long commentId) {
        LambdaQueryWrapper<Reply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Reply::getCommentId, commentId);
        return this.baseMapper.selectCount(queryWrapper);
    }
}




