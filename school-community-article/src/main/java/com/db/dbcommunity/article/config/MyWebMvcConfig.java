package com.db.dbcommunity.article.config;

import com.db.dbcommunity.common.constant.ArticleConstant;
import com.db.dbcommunity.common.constant.UserConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {


    /**
     * 添加统一的请求前缀
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix(ArticleConstant.URI_PREFIX, c -> c.isAnnotationPresent(RestController.class));

    }

}
