package com.iluwatar.soa;

import com.iluwatar.soa.services.classes.GreetingServiceImpl;
import com.iluwatar.soa.services.classes.PersonalizedGreetingServiceImpl;
import com.iluwatar.soa.services.classes.WeatherServiceImpl;
import com.iluwatar.soa.services.interfaces.GreetingService;
import com.iluwatar.soa.services.interfaces.PersonalizedGreetingService;
import com.iluwatar.soa.services.interfaces.WeatherService;
import com.iluwatar.soa.services.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SOAExampleApplication {

  private static final Logger log = LoggerFactory.getLogger(SOAExampleApplication.class);

  public static void main(String[] args) {
    configureAndRegisterServices();
    SpringApplication.run(SOAExampleApplication.class, args);
    log.info("SOA Example Application started successfully!");
    log.info("You can access the application via: http://localhost:8080/home/greeting");
    log.info("This endpoint will return a personalized greeting based on the weather conditions.");
    log.info("You can explore other endpoints as well for different functionalities.");
  }

  private static void configureAndRegisterServices() {
    log.info("Configuring and registering services...");


    GreetingService greetingService = new GreetingServiceImpl();
    WeatherService weatherService = new WeatherServiceImpl();
    PersonalizedGreetingService personalizedGreetingService = new PersonalizedGreetingServiceImpl(greetingService, weatherService);


    ServiceRegistry.registerService("greetingService", greetingService);
    ServiceRegistry.registerService("weatherService", weatherService);
    ServiceRegistry.registerService("personalizedGreetingService", personalizedGreetingService);

    log.info("Services configured and registered successfully.");


    log.info("Current service registry:");
    ServiceRegistry.registry.forEach((serviceName, serviceInstance) -> {
      log.info("- {} : {}", serviceName, serviceInstance.getClass().getName());
    });
  }
}


