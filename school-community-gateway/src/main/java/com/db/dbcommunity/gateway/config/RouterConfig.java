package com.db.dbcommunity.gateway.config;

import com.db.dbcommunity.common.constant.AuthConstant;
import com.db.dbcommunity.common.constant.GatewayConstant;
import com.db.dbcommunity.common.constant.SecurityConstant;
import com.db.dbcommunity.common.constant.UserConstant;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    /**
     * 配置路由规则
     */
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user_api_route", r -> {
//                    r.host(GatewayConstant.API_HOST).and().path(UserConstant.URI_PREFIX + "/**");
//                    return r.uri("lb://school-community-user");
//                })
//                .route("security_api_route", r -> {
//                    r.host(GatewayConstant.API_HOST).and().path(SecurityConstant.URI_PREFIX + "/**");
//                    return r.uri("lb://school-community-security");
//                })
//                .route("auth_api_route", r -> {
//                    r.host(GatewayConstant.API_HOST).and().path(AuthConstant.URI_PREFIX + "/**");
//                    return r.uri("lb://school-community-auth");
//                })
//                .build();
//    }
}
