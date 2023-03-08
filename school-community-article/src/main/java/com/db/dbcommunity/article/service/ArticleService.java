package com.db.dbcommunity.article.service;

import com.db.dbcommunity.article.model.dto.ArticleCreateDTO;
import com.db.dbcommunity.article.model.dto.ArticleUpdateDTO;
import com.db.dbcommunity.article.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.article.model.vo.*;
import com.db.dbcommunity.common.util.MyPage;

import java.util.List;

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
    Long create(ArticleCreateDTO articleVO);

    /**
     * 更新文章
     * @param updateDTO 要更新的文章信息
     * @return 是否更新成功
     */
    boolean update(ArticleUpdateDTO updateDTO);

    /**
     * 根据用户id获取用户发表的文章信息
     * @return 文章信息
     */
    MyPage<UserHomePageArticleInfoVO> getArticlePageByUserId(Long userId, Long current, Short size);

    /**
     * 根据文章id和用户id删除文章
     * @param articleId 文章id
     * @param userId 用户id-可以为空
     * @return 是否删除成功
     */
    boolean deleteArticleById(Long articleId, Long userId);

    /**
     * 根据文章id和作者id获取文章详细信息
     * @param articleId 文章id
     * @param currentUserId 作者id-可以为空
     * @return 文章详细信息
     */
    ArticleDetailInfoVO getArticleDetailById(Long articleId, Long currentUserId);

    /**
     * 获取最新文章
     */
    MyPage<ArticleMainInfoVO> getLatestArticleMainInfo(Long current, Short size);

    /**
     * 获取热门文章(判断依据：目前仅根据浏览量降序)
     */
    MyPage<ArticleMainInfoVO> getPopularArticleMainInfo(Long current, Short size);

    /**
     * 根据用户id获取用户发表的文章数量
     */
    Long getArticleCountByAuthorId(Long authorId);

    /**
     * 获取置顶文章，可以指定分区
     * @param sectionId 分区id，可以为空
     */
    List<ArticleMainInfoVO> getTopArticle(Integer sectionId);

    /**
     * 根据文章id获取标题
     */
    String getTitleByArticleId(Long articleId);

    /**
     * 将文章设为置顶或取消置顶
     */
    boolean changeArticleTopStatus(Long articleId);

    /**
     * 获取发表文章数量TopN的作者id和文章数量
     * @param n 前几
     */
    List<AuthorIdWithArticleCountVO> getActiveAuthors(Integer n);
}
