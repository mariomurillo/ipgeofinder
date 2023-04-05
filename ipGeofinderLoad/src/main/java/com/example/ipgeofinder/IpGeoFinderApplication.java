package com.example.ipgeofinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.example.ipgeofinder.repository")
public class IpGeoFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpGeoFinderApplication.class, args);
	}

}
