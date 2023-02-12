package com.db.dbcommunity.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.db.dbcommunity.*"})
@EnableDiscoveryClient
public class OssApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(OssApplication.class, args);
    }
}
