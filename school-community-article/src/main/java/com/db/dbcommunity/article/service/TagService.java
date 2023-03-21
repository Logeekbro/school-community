package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
* @author bin
* @description 针对表【tb_tag】的数据库操作Service
* @createDate 2023-02-12 11:22:50
*/
public interface TagService extends IService<Tag> {

    void handleArticleTagCreate(Long articleId, Set<String> tags);

    /**
     * 通过文章id获取该文章的标签名列表
     */
    List<String> selectTagNameListByArticleId(Long articleId);

    /**
     * 根据关键字模糊匹配标签名列表
     */
    List<Object> getSimilarTagsByKeyword(String keyword);
}
