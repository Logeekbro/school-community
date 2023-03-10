package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Section;
import com.db.dbcommunity.article.service.SectionService;
import com.db.dbcommunity.article.mapper.SectionMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author bin
* @description 针对表【tb_section】的数据库操作Service实现
* @createDate 2023-03-09 17:10:38
*/
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section>
    implements SectionService{


    @Cacheable(cacheNames = "allSectionInfo", key = "'allSectionInfo'")
    @Override
    public List<Map<String, Object>> getAllSectionInfo() {
        LambdaQueryWrapper<Section> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Section::getSectionId, Section::getSectionName, Section::getCreateBy, Section::getCreateTime);
        return this.baseMapper.selectMaps(queryWrapper);
    }

    @CacheEvict(cacheNames = "allSectionInfo", key = "'allSectionInfo'")
    @Override
    public Integer addSection(Long currentUserId, String sectionName) {
        Section section = new Section();
        section.setSectionName(sectionName);
        section.setCreateBy(currentUserId);
        this.baseMapper.insert(section);
        return section.getSectionId();
    }

    @Cacheable(cacheNames = "sectionNames", key = "'sectionName:' + #sectionId ")
    @Override
    public String getSectionNameById(String sectionId) {
        LambdaQueryWrapper<Section> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Section::getSectionName);
        queryWrapper.eq(Section::getSectionId, sectionId);
        return this.baseMapper.selectOne(queryWrapper).getSectionName();
    }
}




