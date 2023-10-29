package com.iluwater.microservices.shared.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Entry point for the shared database microservice application.
 * This application is configured to exclude the default DataSource auto-configuration as
 * it is using a custom database configuration.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {

  /**
   * Main method which starts the Spring Boot application.
   *
   * @param args Command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
