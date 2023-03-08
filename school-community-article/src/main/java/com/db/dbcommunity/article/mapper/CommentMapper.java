package com.db.dbcommunity.article.mapper;

import com.db.dbcommunity.article.model.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.article.model.vo.CommentInListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_comment】的数据库操作Mapper
* @createDate 2023-03-07 15:25:06
* @Entity com.db.dbcommunity.article.model.entity.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT comment_id,user_id,content,create_time FROM tb_comment WHERE article_id=#{articleId} AND deleted=0 " +
            "LIMIT #{offset}, #{size}")
    List<CommentInListVO> selectCommentList(@Param("articleId") Long articleId, @Param("offset") Long offset, @Param("size") Short size);
}




