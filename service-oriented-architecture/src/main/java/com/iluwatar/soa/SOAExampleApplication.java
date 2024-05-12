package com.iluwatar.soa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SOAExampleApplication {

  private static final Logger log = LoggerFactory.getLogger(SOAExampleApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SOAExampleApplication.class, args);
    log.info("SOA Example Application started successfully!");
    log.info("You can access the application via: http://localhost:8080/home/greeting");
    log.info("This endpoint will return a personalized greeting based on the weather conditions.");
    log.info("You can explore other endpoints as well for different functionalities.");
  }
}
