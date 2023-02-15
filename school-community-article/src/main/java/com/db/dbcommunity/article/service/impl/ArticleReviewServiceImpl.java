package com.db.dbcommunity.article.service.impl;

import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.mapper.ArticleMapper;
import com.db.dbcommunity.article.model.entity.Article;
import com.db.dbcommunity.article.model.vo.ArticleMainInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleReviewResultVO;
import com.db.dbcommunity.article.service.ArticleReviewService;
import com.db.dbcommunity.article.thread.MyThreadPoolExecutor;
import com.db.dbcommunity.article.thread.ServiceContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.db.dbcommunity.article.util.MethodUtil.dataChangeCall;

@Service
public class ArticleReviewServiceImpl implements ArticleReviewService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleMainInfoVO> getNeedReviewArticleListById(Long userId) {
        return articleMapper.selectNeedReviewArticleListById(userId);
    }

    @Override
    public boolean updateReviewStatus(Long articleId, ArticleReviewResultVO vo) {
        Article article = new Article();
        article.setArticleId(articleId);
        if (vo.getIsPass()) article.setStatus(0);
        else article.setStatus(2);
        ServiceContext.setReviewResultVo(vo);
        ServiceContext.setArticleId(articleId);
        return dataChangeCall(DataChangeType.ARTICLE_PASS_REVIEW, ()-> articleMapper.updateById(article) > 0);
//        ReviewHistory reviewHistory = new ReviewHistory(articleId, userId, isPass, description);
//        mongoTemplate.insert(reviewHistory);
//        ReviewResultNotion reviewResultNotion = new ReviewResultNotion(userId, articleId, isPass, description);
//        systemMessageProvider.sendReviewResultNotion(reviewResultNotion);
    }
}
