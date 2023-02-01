package com.db.dbcommunity.user.mapper;

import com.db.dbcommunity.user.model.dto.UserAuthDTO;
import com.db.dbcommunity.user.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}




