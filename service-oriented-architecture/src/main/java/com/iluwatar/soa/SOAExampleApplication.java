package com.iluwatar.soa;

import com.iluwatar.soa.services.config.ServiceConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SOAExampleApplication {

  public static void main(String[] args) {
    ServiceConfiguration.configureAndRegisterServices();
    SpringApplication.run(SOAExampleApplication.class, args);
    LOGGER.info("SOA Example Application started successfully!");
    LOGGER.info("You can access the application via: http://localhost:8080/login");
    LOGGER.info(
        "This endpoint will retsurn a personalized greeting based on the weather conditions.");
    LOGGER.info("You can explore other endpoints as well for different functionalities.");
  }


}


