package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author bin
* @description 针对表【tb_tag】的数据库操作Service
* @createDate 2023-02-12 11:22:50
*/
public interface TagService extends IService<Tag> {

    void handleArticleTagCreate(Long articleId, Set<String> tags);
}
