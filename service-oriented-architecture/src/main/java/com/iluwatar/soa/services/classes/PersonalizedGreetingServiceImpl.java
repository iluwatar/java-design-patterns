package com.iluwatar.soa.services.classes;

import com.iluwatar.soa.model.WeatherCondition;
import com.iluwatar.soa.services.interfaces.GreetingService;
import com.iluwatar.soa.services.interfaces.PersonalizedGreetingService;
import com.iluwatar.soa.services.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalizedGreetingServiceImpl implements PersonalizedGreetingService {

  private final GreetingService greetingService;
  private final WeatherService weatherService;

  @Autowired
  public PersonalizedGreetingServiceImpl(GreetingService greetingService, WeatherService weatherService) {
    this.greetingService = greetingService;
    this.weatherService = weatherService;
  }

  public String generateGreeting() {
    String weatherGreeting = getWeatherGreeting();
    return weatherGreeting + "! " + greetingService.getGenericGreeting();
  }

  private String getWeatherGreeting() {
    WeatherCondition currentWeather = weatherService.getCurrentWeather();
    switch (currentWeather) {
      case SUNNY:
        return "What a good sunny day!";
      case RAINY:
        return "What a rainy day!";
      case CLOUDY:
        return "What a cloudy day!";
      case FOGGY:
        return "What a foggy day!";
      default:
        // error case
        return "unexpected weather condition";
    }
  }
}

