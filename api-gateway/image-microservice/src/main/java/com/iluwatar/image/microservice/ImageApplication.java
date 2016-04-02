package com.iluwatar.image.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ImageApplication starts up Spring Boot, exposing endpoints for the Image microservice through
 * the {@link ImageController}.
 */
@SpringBootApplication
public class ImageApplication {

  /**
   * Microservice entry point
   * @param args
   *          command line args
   */
  public static void main(String[] args) {
    SpringApplication.run(ImageApplication.class, args);
  }
}
