package com.db.dbcommunity.visit.mapper;

import com.db.dbcommunity.visit.model.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.visit.model.vo.UserHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_history】的数据库操作Mapper
* @createDate 2023-03-05 10:29:06
* @Entity com.db.dbcommunity.article.model.entity.History
*/
@Mapper
public interface HistoryMapper extends BaseMapper<History> {


    List<UserHistoryVO> selectUserHistoryByDate(@Param("userId") Long currentUserId, @Param("targetDate") String targetDate);

    @Select("SELECT DATE(create_time) FROM tb_history " +
            "WHERE DATE(create_time) < #{date} AND user_id=#{userId} AND deleted=0 " +
            "ORDER BY create_time DESC LIMIT 1")
    String selectBeforeDate(Long currentUserId, String targetDate);
}




