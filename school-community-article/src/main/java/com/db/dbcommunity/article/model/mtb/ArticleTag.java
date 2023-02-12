package com.db.dbcommunity.article.model.mtb;


import com.db.dbcommunity.common.model.mtb.MiddleTable;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 文章id-标签名称的中间表操作类
 */
@Component
public class ArticleTag implements MiddleTable {
    @Override
    public String tableName() {
        return "mtb_article_tag";
    }

    @Override
    public String firstFiledName() {
        return "article_id";
    }

    @Override
    public String secondFiledName() {
        return "tag_name";
    }
}
