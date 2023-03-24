package com.db.dbcommunity.user.mapper;

import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.user.model.vo.UserBasicInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author bin
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-01-31 23:24:55
* @Entity com.db.dbcommunity.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    UserAuthDTO selectUserAuthByAccount(@Param("account") String account);

    @Select("SELECT user_id, username, nick_name, avatar, introduce, create_time FROM user WHERE user_id=#{userId} AND deleted=0")
    UserBasicInfoVO selectUserBasicInfoById(@Param("userId") Long userId);
}




