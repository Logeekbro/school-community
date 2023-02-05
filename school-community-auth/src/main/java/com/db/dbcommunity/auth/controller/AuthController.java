package com.db.dbcommunity.auth.controller;


import com.db.dbcommunity.common.api.R;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.Principal;
import com.nimbusds.jose.jwk.JWKSet;


import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Resource
    private KeyPair keyPair;

    @PostMapping("/token")
    public Object postAccessToken(
            Principal principal,
            @RequestBody Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        // 暂时只支持密码模式
        parameters.put("grant_type", "password");
        // 移除一些不应该存在的参数（防止恶意调用接口）
        parameters.remove("scope");
        parameters.remove("client_id");
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return R.success(accessToken);
    }

    @GetMapping("/public-key")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}