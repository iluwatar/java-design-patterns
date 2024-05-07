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
    log.info("Welcome to the SOA Example Application!");
    log.info("To get to the services visit: http://localhost:8080/services on your browser");
  }

}
