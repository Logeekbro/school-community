package com.db.dbcommunity.article.handler;

import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.model.mtb.ArticleTag;
import com.db.dbcommunity.article.thread.ServiceContext;
import com.db.dbcommunity.common.IDataChangeHandler;
import com.db.dbcommunity.common.mapper.MiddleTableMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DeleteArticleHandler implements IDataChangeHandler {

    @Resource
    private MiddleTableMapper middleTableMapper;

    @Resource
    private ArticleTag articleTag;

    @Override
    public String[] handleType() {
        return new String[]{DataChangeType.DELETE_ARTICLE.getDesc()};
    }

    @Override
    public Runnable runnable() {
        return () -> {
            Long articleId = ServiceContext.getArticleId();
            // 删除与标签的联系
            middleTableMapper.deleteRelationById1(articleTag,articleId);
        };

    }
}
