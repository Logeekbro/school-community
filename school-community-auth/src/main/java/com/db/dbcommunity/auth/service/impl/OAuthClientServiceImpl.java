package com.db.dbcommunity.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.db.dbcommunity.auth.entity.OAuthClient;
import com.db.dbcommunity.auth.mapper.OAuthClientMapper;
import com.db.dbcommunity.auth.service.OAuthClientService;
import org.springframework.stereotype.Service;

@Service
public class OAuthClientServiceImpl extends ServiceImpl<OAuthClientMapper, OAuthClient> implements OAuthClientService {
}
