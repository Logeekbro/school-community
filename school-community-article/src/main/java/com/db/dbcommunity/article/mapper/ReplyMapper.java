package com.db.dbcommunity.article.mapper;

import com.db.dbcommunity.article.model.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.article.model.vo.ReplyInListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_reply】的数据库操作Mapper
* @createDate 2023-03-07 15:25:06
* @Entity com.db.dbcommunity.article.model.entity.Reply
*/
public interface ReplyMapper extends BaseMapper<Reply> {

    @Select("SELECT reply_id,user_id,target,content,create_time FROM tb_reply " +
            "WHERE comment_id=#{commentId} AND deleted=0 " +
            "LIMIT #{offset}, #{size}")
    List<ReplyInListVO> selectReplyListByCommentId(@Param("commentId") Long commentId, @Param("offset") Long offset, @Param("size") Short size);
}




