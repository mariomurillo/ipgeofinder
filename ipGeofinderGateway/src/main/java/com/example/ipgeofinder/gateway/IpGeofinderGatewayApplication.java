package com.example.ipgeofinder.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IpGeofinderGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpGeofinderGatewayApplication.class, args);
	}

}
