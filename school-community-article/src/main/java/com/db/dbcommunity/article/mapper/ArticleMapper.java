package com.db.dbcommunity.article.mapper;

import com.db.dbcommunity.article.enums.OrderType;
import com.db.dbcommunity.article.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.db.dbcommunity.article.model.vo.ArticleDetailInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleMainInfoVO;
import com.db.dbcommunity.article.model.vo.AuthorIdWithArticleCountVO;
import com.db.dbcommunity.article.model.vo.UserHomePageArticleInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
     */
    List<UserHomePageArticleInfoVO> selectArticlePageByUserId(@Param("offset") Long offset, @Param("size") Short size,
                                                              @Param("userId") Long userId);

    /**
     * 根据用户id和文章id查询文章详细信息
     */
    ArticleDetailInfoVO selectArticleDetailById(@Param("articleId") Long articleId, @Param("userId") Long userId);

    /**
     * 根据排序条件分页查询 文章列表页需要的文章信息
     */
    List<ArticleMainInfoVO> selectArticleMainInfoPageWithOrder(@Param("offset") Long offset,
                                                               @Param("size") Short size,
                                                               @Param("order") OrderType order);

    List<ArticleMainInfoVO> selectTopArticle(@Param("sectionId") Integer sectionId);

    @Select("SELECT title FROM tb_article WHERE article_id=#{articleId} AND deleted=0")
    String selectTitleById(@Param("articleId") Long articleId);

    List<ArticleMainInfoVO> selectNeedReviewArticleListById(@Param("userId") Long userId);

    @Update("UPDATE tb_article SET top=(top=0) WHERE article_id=#{articleId}")
    boolean updateArticleTopStatus(@Param("articleId") Long articleId);

    @Select("SELECT author_id, COUNT(*) articleCount FROM tb_article " +
            "WHERE deleted=0 AND status IN (0,3) GROUP BY author_id ORDER BY COUNT(*) DESC LIMIT #{n}")
    List<AuthorIdWithArticleCountVO> selectTopNAuthor(Integer n);

    List<ArticleMainInfoVO> selectUnPassReviewArticleListById(Long currentUserId);
}




