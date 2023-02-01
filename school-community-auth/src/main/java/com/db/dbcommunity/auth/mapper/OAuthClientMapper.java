package com.db.dbcommunity.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.auth.entity.OAuthClient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuthClientMapper extends BaseMapper<OAuthClient> {
}
