package com.db.dbcommunity.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.db.dbcommunity.auth.entity.OAuthClient;
import org.springframework.security.oauth2.provider.ClientDetails;

public interface OAuthClientService extends IService<OAuthClient> {

    ClientDetails loadClientByClientId(String clientId);
}
