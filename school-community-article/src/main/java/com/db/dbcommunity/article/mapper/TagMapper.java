package com.db.dbcommunity.article.mapper;

import com.db.dbcommunity.article.model.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
* @author bin
* @description 针对表【tb_tag】的数据库操作Mapper
* @createDate 2023-02-12 11:22:50
* @Entity com.db.dbcommunity.article.model.entity.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 标签名不存在时插入数据库
     * @param tagName 标签名
     * @return 是否插入成功
     */
    @Insert("INSERT IGNORE INTO tb_tag VALUES (#{tagName}, NOW(), NOW(), 0)")
    int insertIfNotExist(String tagName);
}




