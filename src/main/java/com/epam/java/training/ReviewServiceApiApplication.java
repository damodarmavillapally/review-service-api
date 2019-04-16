package com.epam.java.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReviewServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewServiceApiApplication.class, args);
	}

}
