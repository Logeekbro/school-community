package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.article.model.vo.ArticleCreateVO;
import com.db.dbcommunity.article.model.vo.ArticleUpdateVO;

/**
* @author bin
* @description 针对表【tb_article】的数据库操作Service
* @createDate 2023-02-12 09:45:15
*/
public interface ArticleService extends IService<Article> {

    /**
     * 创建文章
     * @param articleVO 创建文章需要的信息
     * @return 创建好的文章id
     */
    Long create(ArticleCreateVO articleVO);

    /**
     * 更新文章
     * @param updateDTO 要更新的文章信息
     * @return 是否更新成功
     */
    boolean update(ArticleUpdateVO updateDTO);
}
