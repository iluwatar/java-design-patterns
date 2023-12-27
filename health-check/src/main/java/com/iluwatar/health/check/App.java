package com.iluwatar.health.check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This application provides health check APIs for various aspects of the microservice architecture,
 * including database transactions, garbage collection, and overall system health. These health
 * checks are essential for monitoring the health and performance of the microservices and ensuring
 * their availability and responsiveness. For more information about health checks and their role in
 * microservice architectures, please refer to: [Microservices Health Checks
 * API]('https://microservices.io/patterns/observability/health-check-api.html').
 *
 * @author ydoksanbir
 */
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class App {
  /** Program entry point. */
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
