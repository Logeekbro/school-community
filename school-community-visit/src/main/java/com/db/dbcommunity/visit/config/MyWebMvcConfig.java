package com.db.dbcommunity.visit.config;

import com.db.dbcommunity.common.constant.UserConstant;
import com.db.dbcommunity.common.constant.VisitConstant;
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
                .addPathPrefix(VisitConstant.URI_PREFIX, c -> c.isAnnotationPresent(RestController.class));

    }

}
