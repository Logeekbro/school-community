package com.db.dbcommunity.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.db.dbcommunity.*"})
@EnableDiscoveryClient
// 此处一定要指定具体路径，否则会扫到奇怪的东西...然后报错
@MapperScan(basePackages = {"com.db.dbcommunity.*.mapper"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
