package com.db.dbcommunity.common.mapper;

import com.db.dbcommunity.common.model.mtb.MiddleTable;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;


/**
 * 中间表通用操作mapper
 */

@Mapper
public interface MiddleTableMapper {

    @Insert("INSERT INTO " +
            "${table.tableName()}" +
            "(${table.firstFiledName()}, ${table.secondFiledName()}) " +
            "VALUES(#{id1}, #{id2})")
    void insertRelationById(@Param("table") MiddleTable table,
                            @Param("id1") Serializable id1,
                            @Param("id2") Serializable id2);


    @Select("SELECT COUNT(*) FROM ${table.tableName()} " +
            "WHERE ${table.firstFiledName()}=#{id1} AND ${table.secondFiledName()}=#{id2} ")
    boolean checkRelationById(@Param("table") MiddleTable table,
                              @Param("id1") Serializable id1,
                              @Param("id2") Serializable id2);

    @Delete("DELETE FROM ${table.tableName()} " +
            "WHERE ${table.firstFiledName()}=#{id1} AND ${table.secondFiledName()}=#{id2}")
    boolean deleteRelationById(@Param("table") MiddleTable table,
                               @Param("id1") Serializable id1,
                               @Param("id2") Serializable id2);

    @Select("SELECT COUNT(*) FROM ${table.tableName()} " +
            "WHERE ${table.firstFiledName()}=#{id1} ")
    Long selectCountById1(@Param("table") MiddleTable table,
                          @Param("id1") Serializable id1);

    @Select("SELECT COUNT(*) FROM ${table.tableName()} " +
            "WHERE ${table.secondFiledName()}=#{id2} ")
    Long selectCountById2(@Param("table") MiddleTable table,
                          @Param("id2") Serializable id2);

    @Select("SELECT ${table.firstFiledName()} FROM ${table.tableName()} " +
            "WHERE ${table.secondFiledName()}=#{id2} ")
    List<String> selectId1ListById2(@Param("table")MiddleTable table,
                                    @Param("id2") Serializable id2);

    @Select("SELECT ${table.secondFiledName()} FROM ${table.tableName()} " +
            "WHERE ${table.firstFiledName()}=#{id1} " +
            "${table.visible()}")
    List<String> selectId2ListById1(@Param("table") MiddleTable table,
                                    @Param("id1") Serializable id1);


    @Insert("INSERT IGNORE INTO " +
            "${table.tableName()}" +
            "(${table.firstFiledName()}, ${table.secondFiledName()}) " +
            "VALUES(#{id1}, #{id2})")
    Boolean insertRelationIfNotExist(@Param("table") MiddleTable table,
                                     @Param("id1") Serializable id1,
                                     @Param("id2") Serializable id2);
}
