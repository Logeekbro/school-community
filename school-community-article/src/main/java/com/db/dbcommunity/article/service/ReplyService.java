package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.dto.ReplyCreateDTO;
import com.db.dbcommunity.article.model.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.article.model.vo.ReplyInListVO;
import com.db.dbcommunity.common.util.MyPage;

/**
* @author bin
* @description 针对表【tb_reply】的数据库操作Service
* @createDate 2023-03-07 15:25:06
*/
public interface ReplyService extends IService<Reply> {

    /**
     * 添加回复
     */
    boolean saveReply(Long currentUserId, ReplyCreateDTO dto);

    /**
     * 根据id删除回复
     */
    boolean deleteById(Long currentUserId, Long replyId);

    /**
     * 根据评论id获取回复列表
     */
    MyPage<ReplyInListVO> getReplyListByCommentId(Long commentId, Long current, Short size);

    /**
     * 根据评论id获取评论的回复数量
     */
    Long getReplyCountByCommentId(Long commentId);
}
