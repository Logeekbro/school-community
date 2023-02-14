package com.db.dbcommunity.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Article;
import com.db.dbcommunity.article.model.vo.ArticleCreateVO;
import com.db.dbcommunity.article.model.vo.ArticleDetailInfoVO;
import com.db.dbcommunity.article.model.vo.ArticleUpdateVO;
import com.db.dbcommunity.article.model.vo.UserHomePageArticleInfoVO;
import com.db.dbcommunity.article.service.ArticleService;
import com.db.dbcommunity.article.mapper.ArticleMapper;
import com.db.dbcommunity.article.service.TagService;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.common.util.MyBeanUtil;
import com.db.dbcommunity.common.util.MyPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Supplier;

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
    public MyPage<UserHomePageArticleInfoVO> getArticlePageByUserId(Long userId, Long current, Integer size) {
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

    /**
     * 调用会改变数据的方法时的统一包装方法，以便于添加 数据改变时 的统一操作，如：更新缓存、发送消息等
     * @param type 调用的方法标识
     * @param supplier 调用的方法
     * @return 是否执行成功
     */
    private boolean dataChangeCall(DataChangeType type, Supplier<Boolean> supplier) {
        Boolean result = supplier.get();
        switch (type) {
            case INSERT_ARTICLE:
        }
        return result;
    }

    private enum DataChangeType {
        INSERT_ARTICLE(0, "新增文章"),
        UPDATE_ARTICLE(1, "更新文章"),
        DELETE_ARTICLE(2, "删除文章");

        private final int code;
        private final String desc;

        DataChangeType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }
}




