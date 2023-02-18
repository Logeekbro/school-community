package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.enums.OrderType;
import com.db.dbcommunity.article.model.entity.Article;
import com.db.dbcommunity.article.model.vo.*;
import com.db.dbcommunity.article.service.ArticleService;
import com.db.dbcommunity.article.mapper.ArticleMapper;
import com.db.dbcommunity.article.service.TagService;
import com.db.dbcommunity.article.thread.ServiceContext;
import com.db.dbcommunity.article.util.MethodUtil;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.common.util.MyBeanUtil;
import com.db.dbcommunity.common.util.MyPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Supplier;

import static com.db.dbcommunity.article.util.MethodUtil.dataChangeCall;

/**
* @author bin
* @description 针对表【tb_article】的数据库操作Service实现
* @createDate 2023-02-12 09:45:15
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private TagService tagService;


    @Transactional
    @Override
    public Long create(ArticleCreateVO articleVO) {
        Article article = MyBeanUtil.copyProps(articleVO, Article.class);
        // 判断文章是否需要审核
        if (articleVO.getNeedReview()) {
            article.setStatus(1);
        } else {
            // 未经审核的文章需将状态标记为3，后续待管理员查看
            article.setStatus(3);
        }
        // 保存文章
        if (dataChangeCall(DataChangeType.INSERT_ARTICLE, ()-> baseMapper.insert(article) > 0)) {
            // 保存标签
            tagService.handleArticleTagCreate(article.getArticleId(), articleVO.getTags());
            // TODO 索引至ES
        } else {
            ApiAsserts.fail(ResultCode.FAILED);
        }
        return article.getArticleId();
    }

    @Override
    public boolean update(ArticleUpdateVO updateDTO) {
        Article article = MyBeanUtil.copyProps(updateDTO, Article.class);
        if (updateDTO.getNeedReview()) article.setStatus(1);
        return dataChangeCall(DataChangeType.UPDATE_ARTICLE, () -> this.baseMapper.updateById(article) > 0);
    }

    @Override
    public MyPage<UserHomePageArticleInfoVO> getArticlePageByUserId(Long userId, Long current, Short size) {
        Article article = new Article();
        article.setAuthorId(userId);
        Long total = this.baseMapper.selectTotal(article);
        MyPage<UserHomePageArticleInfoVO> page = new MyPage<>(current, size, total);
        List<UserHomePageArticleInfoVO> vos = this.baseMapper.selectArticlePageByUserId(page.offset(), page.getSize(), userId);
        page.setRecords(vos);
        return page;
    }

    @Override
    public boolean deleteArticleById(Long articleId, Long userId) {
        ServiceContext.setArticleId(articleId);
        if(userId != null) {
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getArticleId, articleId).eq(Article::getAuthorId, userId);
            return dataChangeCall(DataChangeType.DELETE_ARTICLE, () -> this.baseMapper.delete(queryWrapper) > 0);
        } else {
            return dataChangeCall(DataChangeType.DELETE_ARTICLE, () -> this.baseMapper.deleteById(articleId) > 0);
        }
    }

    @Override
    public ArticleDetailInfoVO getArticleDetailById(Long articleId, Long currentUserId) {
        return this.baseMapper.selectArticleDetailById(articleId, currentUserId);
    }

    // TODO 高频数据，需要缓存
    @Override
    public MyPage<ArticleMainInfoVO> getLatestArticleMainInfo(Long current, Short size) {
        Long total = this.baseMapper.selectTotal(null);
        MyPage<ArticleMainInfoVO> page = new MyPage<>(current, size, total);
        List<ArticleMainInfoVO> vos =
                this.baseMapper.selectArticleMainInfoPageWithOrder(page.offset(), page.getSize(), OrderType.CREATE_TIME_DESC);
        page.setRecords(vos);
        return page;
    }

    // TODO 高频数据，需要缓存
    @Override
    public MyPage<ArticleMainInfoVO> getPopularArticleMainInfo(Long current, Short size) {
        Long total = this.baseMapper.selectTotal(null);
        MyPage<ArticleMainInfoVO> page = new MyPage<>(current, size, total);
        List<ArticleMainInfoVO> vos =
                this.baseMapper.selectArticleMainInfoPageWithOrder(page.offset(), page.getSize(), OrderType.VIEW_COUNT_DESC);
        page.setRecords(vos);
        return page;
    }

    @Override
    public Long getArticleCountByAuthorId(Long authorId) {
        Article article = new Article();
        article.setAuthorId(authorId);
        return this.baseMapper.selectTotal(article);
    }

    @Override
    public List<ArticleMainInfoVO> getTopArticle(Integer sectionId) {
        return this.baseMapper.selectTopArticle(sectionId);
    }

    @Override
    public String getTitleByArticleId(Long articleId) {
        return this.baseMapper.selectTitleById(articleId);
    }

    @Override
    public boolean changeArticleTopStatus(Long articleId) {
        return this.baseMapper.updateArticleTopStatus(articleId);
    }

    @Override
    public List<AuthorIdWithArticleCountVO> getActiveAuthors(Integer n) {
        return this.baseMapper.selectTopNAuthor(n);
    }


}




