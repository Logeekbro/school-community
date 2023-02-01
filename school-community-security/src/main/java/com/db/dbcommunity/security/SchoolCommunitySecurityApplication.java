package com.db.dbcommunity.security;

import com.db.dbcommunity.security.service.VerifyService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@SpringBootApplication
public class SchoolCommunitySecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolCommunitySecurityApplication.class, args);
    }

    @Component
    static class OnStart implements ApplicationRunner {

        @Resource
        private VerifyService verifyService;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            // 将数据库中用于验证的汉字集合加载到Redis
            verifyService.loadVerifyWordFromDB();
        }
    }

}
