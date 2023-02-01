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
    @Cacheable(cacheNames = "auth", key = "'oauth-client:'+#clientId")
    public ClientDetails loadClientByClientId(String clientId) {
        OAuthClient client = oAuthClientService.getById(clientId);
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
