package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.entity.Section;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author bin
* @description 针对表【tb_section】的数据库操作Service
* @createDate 2023-03-09 17:10:38
*/
public interface SectionService extends IService<Section> {

    /**
     * 获取所有分区信息
     */
    List<Map<String, Object>> getAllSectionInfo();

    /**
     * 创建新分区
     */
    Integer addSection(Long currentUserId, String sectionName);

    /**
     * 根据分区id获取分区名称
     */
    String getSectionNameById(String sectionId);
}
