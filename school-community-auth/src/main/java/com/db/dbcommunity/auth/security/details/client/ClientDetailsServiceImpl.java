package com.db.dbcommunity.auth.security.details.client;

import com.db.dbcommunity.auth.common.enums.PasswordEncoderTypeEnum;
import com.db.dbcommunity.auth.entity.OAuthClient;
import com.db.dbcommunity.auth.service.OAuthClientService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private OAuthClientService oAuthClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        // 在此
        return oAuthClientService.loadClientByClientId(clientId);
    }
}
