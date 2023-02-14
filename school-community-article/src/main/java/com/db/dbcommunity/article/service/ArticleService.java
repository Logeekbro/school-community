package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.article.model.vo.ArticleCreateVO;
import com.db.dbcommunity.article.model.vo.ArticleUpdateVO;
import com.db.dbcommunity.article.model.vo.UserHomePageArticleInfoVO;
import com.db.dbcommunity.common.util.MyPage;

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

    /**
     * 根据用户id获取用户发表的文章信息
     * @param userId 用户id
     * @param current 第几页
     * @param size 每页几条记录
     * @return 文章信息
     */
    MyPage<UserHomePageArticleInfoVO> getArticlePageByUserId(Long userId, Long current, Integer size);

    /**
     * 根据文章id和用户id删除文章
     * @param articleId 文章id
     * @param userId 用户id-可以为空
     * @return 是否删除成功
     */
    boolean deleteArticleById(Long articleId, Long userId);
}
