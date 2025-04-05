package com.learning.greetingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GreetingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreetingserviceApplication.class, args);
	}

}
