package com.db.dbcommunity.gateway.security;

import cn.hutool.core.util.StrUtil;
import com.db.dbcommunity.common.api.ResultCode;
import com.db.dbcommunity.common.constant.AuthConstant;
import com.db.dbcommunity.common.constant.SecurityConstant;
import com.db.dbcommunity.gateway.util.ResponseUtil;
import com.db.dbcommunity.gateway.util.UrlPatternUtil;
import com.nimbusds.jose.JWSObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URLEncoder;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse exchangeResponse = exchange.getResponse();
        // 判断请求是否需要进行安全认证
        if(!isSecurityPath(request.getPath().toString())) {
            // 验证请求是否通过安全认证
            String verifyToken = request.getHeaders().getFirst(SecurityConstant.VERIFY_TOKEN_KEY);
            if(verifyToken != null) {
                // 检查安全令牌的正确性
                Object token = redisTemplate.opsForValue().get(SecurityConstant.CACHE_VERIFY_TOKEN_PREFIX + verifyToken);
                if(token == null) {
                    return Mono.defer(() -> Mono.just(exchangeResponse))
                            .flatMap(response -> ResponseUtil.writeErrorInfo(response, ResultCode.UN_SECURITY_REQUEST));
                }
            } else {
                return Mono.defer(() -> Mono.just(exchangeResponse))
                        .flatMap(response -> ResponseUtil.writeErrorInfo(response, ResultCode.UN_SECURITY_REQUEST));
            }
        }
        // 不是正确的的JWT不做解析处理
        String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, AuthConstant.JWT_PREFIX)) {
            return chain.filter(exchange);
        }
        //TODO 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
        token = StrUtil.replaceIgnoreCase(token, AuthConstant.JWT_PREFIX, Strings.EMPTY);
        String payload = StrUtil.toString(JWSObject.parse(token).getPayload());
        request = exchange.getRequest().mutate()
                .header(AuthConstant.JWT_PAYLOAD_KEY, URLEncoder.encode(payload, "UTF-8"))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private boolean isSecurityPath(String path) {
        return UrlPatternUtil.match("/security/verify/**", path);
    }
}
