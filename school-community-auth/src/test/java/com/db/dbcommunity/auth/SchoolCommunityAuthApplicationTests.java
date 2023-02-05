package com.db.dbcommunity.auth;

import com.db.dbcommunity.auth.service.OAuthClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@SpringBootTest
class SchoolCommunityAuthApplicationTests {

    @Resource
    private OAuthClientService oAuthClientService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCache() {
        ClientDetails browser_client = oAuthClientService.loadClientByClientId("browser_client");
        System.out.println(browser_client);
        ClientDetails browser_client1 = oAuthClientService.loadClientByClientId("browser_client");
        System.out.println(browser_client1);
    }

}
