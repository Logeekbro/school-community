package com.db.dbcommunity.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
public interface VerifyWordMapper {

    @Select("SELECT word FROM tb_verifyword")
    Set<String> getWordSet();
}
