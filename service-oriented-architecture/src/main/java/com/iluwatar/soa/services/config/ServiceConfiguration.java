package com.iluwatar.soa.services.config;

import com.iluwatar.soa.services.classes.GreetingServiceImpl;
import com.iluwatar.soa.services.classes.PersonalizedGreetingServiceImpl;
import com.iluwatar.soa.services.classes.WeatherServiceImpl;
import com.iluwatar.soa.services.interfaces.GreetingService;
import com.iluwatar.soa.services.interfaces.PersonalizedGreetingService;
import com.iluwatar.soa.services.interfaces.WeatherService;
import com.iluwatar.soa.services.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ServiceConfiguration {

  private ServiceConfiguration() {
    throw new IllegalStateException("Utility class");
  }

  public static void configureAndRegisterServices() {
    LOGGER.info("Configuring and registering services...");

    GreetingService greetingService = new GreetingServiceImpl();
    WeatherService weatherService = new WeatherServiceImpl();
    PersonalizedGreetingService personalizedGreetingService =
        new PersonalizedGreetingServiceImpl(greetingService, weatherService);

    ServiceRegistry.registerService("greetingService", greetingService);
    ServiceRegistry.registerService("weatherService", weatherService);
    ServiceRegistry.registerService("personalizedGreetingService", personalizedGreetingService);

    LOGGER.info("Services configured and registered successfully.");

    logServiceRegistry();
  }

  private static void logServiceRegistry() {
    LOGGER.info("Current service registry:");
    ServiceRegistry.registry.forEach(
        (serviceName, serviceInstance) -> LOGGER.info("- {} : {}", serviceName,
            serviceInstance.getClass().getName()));
  }
}
