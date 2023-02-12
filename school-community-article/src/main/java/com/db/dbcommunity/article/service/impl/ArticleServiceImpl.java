package com.db.dbcommunity.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.article.model.entity.Article;
import com.db.dbcommunity.article.model.vo.ArticleCreateVO;
import com.db.dbcommunity.article.service.ArticleService;
import com.db.dbcommunity.article.mapper.ArticleMapper;
import com.db.dbcommunity.article.service.TagService;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.common.util.MyBeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public Long create(ArticleCreateVO articleVO) {
        Article article = MyBeanUtil.copyProps(articleVO, Article.class);
//        if (file != null) {
//            String mainPic = uploadService.uploadArticleMainPic(file);
//            article.setMainPic(mainPic);
//        }
        if (articleVO.getNeedReview()) {
            article.setStatus(1);
            article.setDeleted(true);
        } else {
            // 未经审核的文章需将状态标记为3，后续待管理员查看
            article.setStatus(3);
        }
        if (this.baseMapper.insert(article) > 0) {
            tagService.handleArticleTagCreate(article.getArticleId(), articleVO.getTags());
            // 刷新缓存
//            cacheService.refreshArticleIdSetCache();
            // TODO 索引至ES
        } else {
            ApiAsserts.fail(ResultCode.FAILED);
        }
        return article.getArticleId();
    }
}




