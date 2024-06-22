package com.iluwatar.soa;

import com.iluwatar.soa.services.classes.GreetingServiceImpl;
import com.iluwatar.soa.services.classes.PersonalizedGreetingServiceImpl;
import com.iluwatar.soa.services.classes.WeatherServiceImpl;
import com.iluwatar.soa.services.interfaces.GreetingService;
import com.iluwatar.soa.services.interfaces.PersonalizedGreetingService;
import com.iluwatar.soa.services.interfaces.WeatherService;
import com.iluwatar.soa.services.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SOAExampleApplication {

  public static void main(String[] args) {
    configureAndRegisterServices();
    SpringApplication.run(SOAExampleApplication.class, args);
    LOGGER.info("SOA Example Application started successfully!");
    LOGGER.info("You can access the application via: http://localhost:8080/login");
    LOGGER.info(
        "This endpoint will return a personalized greeting based on the weather conditions.");
    LOGGER.info("You can explore other endpoints as well for different functionalities.");
  }

  private static void configureAndRegisterServices() {
    LOGGER.info("Configuring and registering services...");


    GreetingService greetingService = new GreetingServiceImpl();
    WeatherService weatherService = new WeatherServiceImpl();
    PersonalizedGreetingService personalizedGreetingService =
        new PersonalizedGreetingServiceImpl(greetingService, weatherService);


    ServiceRegistry.registerService("greetingService", greetingService);
    ServiceRegistry.registerService("weatherService", weatherService);
    ServiceRegistry.registerService("personalizedGreetingService", personalizedGreetingService);

    LOGGER.info("Services configured and registered successfully.");


    LOGGER.info("Current service registry:");
    ServiceRegistry.registry.forEach(
        (serviceName, serviceInstance) -> LOGGER.info("- {} : {}", serviceName,
            serviceInstance.getClass().getName()));
  }
}


