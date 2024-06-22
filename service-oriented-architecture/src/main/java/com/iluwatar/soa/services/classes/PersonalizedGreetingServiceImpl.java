package com.iluwatar.soa.services.classes;

import com.iluwatar.soa.model.WeatherCondition;
import com.iluwatar.soa.services.interfaces.GreetingService;
import com.iluwatar.soa.services.interfaces.PersonalizedGreetingService;
import com.iluwatar.soa.services.interfaces.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalizedGreetingServiceImpl implements PersonalizedGreetingService {

  private final GreetingService greetingService;
  private final WeatherService weatherService;

 @Override
  public String generateGreeting() {
    String weatherGreeting = getWeatherGreeting();
    return weatherGreeting + "! " + greetingService.getGenericGreeting();
  }

  private String getWeatherGreeting() {
    WeatherCondition currentWeather = weatherService.getCurrentWeather();
    return switch (currentWeather) {
      case SUNNY -> "What a good sunny day!";
      case RAINY -> "What a rainy day!";
      case CLOUDY -> "What a cloudy day!";
      case FOGGY -> "What a foggy day!";
      default -> {
        LOGGER.error("Unexpected weather condition: {}", currentWeather);
        yield "unexpected weather condition";
      }
    };
  }
}
