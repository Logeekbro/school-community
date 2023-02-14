package com.db.dbcommunity.article.mapper;

import com.db.dbcommunity.article.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.article.model.vo.ArticleDetailInfoVO;
import com.db.dbcommunity.article.model.vo.UserHomePageArticleInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author bin
* @description 针对表【tb_article】的数据库操作Mapper
* @createDate 2023-02-12 09:45:15
* @Entity com.db.dbcommunity.article.model.entity.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据多种条件查询文章数量
     * @param article 将实体的非空属性作为查询条件
     * @return 查到的文章数量
     */
    Long selectTotal(Article article);

    /**
     * 根据用户id分页查询用户主页文章信息
     * @param offset 偏移量
     * @param size 记录数
     * @param userId 用户id
     * @return 文章信息
     */
    List<UserHomePageArticleInfoVO> selectArticlePageByUserId(@Param("offset") Long offset, @Param("size") Integer size,
                                                              @Param("userId") Long userId);

    ArticleDetailInfoVO selectArticleDetailById(@Param("articleId") Long articleId, @Param("userId") Long userId);
}




