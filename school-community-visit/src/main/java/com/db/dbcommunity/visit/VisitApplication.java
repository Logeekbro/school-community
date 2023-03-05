package com.db.dbcommunity.visit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.db.dbcommunity.*"})
@EnableDiscoveryClient
public class VisitApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitApplication.class, args);
	}

}
