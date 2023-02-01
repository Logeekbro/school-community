package com.db.dbcommunity.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableFeignClients(basePackages = {"com.db.dbcommunity.auth.feign"})
public class SchoolCommunityAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolCommunityAuthApplication.class, args);
    }

}
