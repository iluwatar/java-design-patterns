package com.callusage.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.callusage.application", "com.callusage.utility" })
public class UsageCostProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageCostProcessorApplication.class, args);
	}

}
