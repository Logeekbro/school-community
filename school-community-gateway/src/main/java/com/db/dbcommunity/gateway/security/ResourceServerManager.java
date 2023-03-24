package com.db.dbcommunity.gateway.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.db.dbcommunity.common.constant.AuthConstant;
import com.db.dbcommunity.common.constant.GlobalConstant;
import com.db.dbcommunity.gateway.util.UrlPatternUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@ConfigurationProperties(prefix = "security")
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final StringRedisTemplate stringRedisTemplate;

    @Setter
    private List<String> ignoreUrls;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        if (request.getMethod() == HttpMethod.OPTIONS) { // 预检请求放行
            return Mono.just(new AuthorizationDecision(true));
        }
        String method = request.getMethodValue();
        String path = request.getURI().getPath();

        // 跳过token校验，放在这里去做是为了能够动态刷新
        if (skipValid(path)) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 如果token为空 或者token不合法 则进行拦截
        String restfulPath = method + ":" + path; // RESTFul接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html
        String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, AuthConstant.JWT_PREFIX)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 判断JWT中携带的用户角色是否有权限访问
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    String roleCode = authority.substring(AuthConstant.AUTHORITY_PREFIX.length()); // 用户的角色
                    if (roleCode.equals(AuthConstant.ROOT_ROLE_CODE)) return true;
//                    Boolean isMember = stringRedisTemplate.opsForSet().isMember(GlobalConstant.ROLE_URL_PERMS_KEY + roleCode, restfulPath);
                    // 综合考虑各种原因后，此处使用一种相对低效但适用范围广的方式来简单地完成接口权限验证
                    Set<String> permissions = stringRedisTemplate.opsForSet().members(GlobalConstant.ROLE_URL_PERMS_KEY + roleCode);
                    if(permissions == null) return false;
                    return permissions.stream()
                            .anyMatch(s -> UrlPatternUtil.match(s, restfulPath));
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 跳过校验
     */
    private boolean skipValid(String path) {
        for (String skipPath : ignoreUrls) {
            if (UrlPatternUtil.match(skipPath, path)) {
                return true;
            }
        }
        return false;
    }
}
