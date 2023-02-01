package com.db.dbcommunity.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SchoolCommunityUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolCommunityUserApplication.class, args);
    }

}
