package com.db.dbcommunity.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.auth.common.enums.PasswordEncoderTypeEnum;
import com.db.dbcommunity.auth.entity.OAuthClient;
import com.db.dbcommunity.auth.mapper.OAuthClientMapper;
import com.db.dbcommunity.auth.service.OAuthClientService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service("oAuthClientService")
public class OAuthClientServiceImpl extends ServiceImpl<OAuthClientMapper, OAuthClient> implements OAuthClientService {

    @Cacheable(cacheNames = "authClientDetails", key = "'oauth-client:'+#clientId")
    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        OAuthClient client = this.getById(clientId);
        BaseClientDetails clientDetails = new BaseClientDetails(
                client.getClientId(),
                client.getResourceIds(),
                client.getScope(),
                client.getAuthorizedGrantTypes(),
                client.getAuthorities(),
                client.getWebServerRedirectUri()

        );
        clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
        return clientDetails;
    }
}
