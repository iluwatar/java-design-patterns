package com.callusage.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan({ "com.callusage.application", "com.callusage.utility" })
public class UsageDetailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageDetailSenderApplication.class, args);
	}

}
