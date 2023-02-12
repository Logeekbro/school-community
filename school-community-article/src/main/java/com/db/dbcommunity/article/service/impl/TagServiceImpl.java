package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Tag;
import com.db.dbcommunity.article.model.mtb.ArticleTag;
import com.db.dbcommunity.article.service.TagService;
import com.db.dbcommunity.article.mapper.TagMapper;
import com.db.dbcommunity.common.mapper.MiddleTableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
* @author bin
* @description 针对表【tb_tag】的数据库操作Service实现
* @createDate 2023-02-12 11:22:50
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Resource
    private MiddleTableMapper middleTableMapper;

    @Resource
    private ArticleTag articleTag;

    @Override
    public void handleArticleTagCreate(Long articleId, Set<String> tags) {
        for (String tagName : tags) {
            this.baseMapper.insertIfNotExist(tagName);
            middleTableMapper.insertRelationIfNotExist(articleTag, articleId, tagName);
        }
    }
}




