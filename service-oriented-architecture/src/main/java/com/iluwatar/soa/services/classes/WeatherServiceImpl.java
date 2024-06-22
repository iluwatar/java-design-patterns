package com.iluwatar.soa.services.classes;

import com.iluwatar.soa.model.WeatherCondition;
import java.util.Random;
import com.iluwatar.soa.services.interfaces.WeatherService;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
  private final Random random = new Random();

  public WeatherCondition getCurrentWeather() {
    WeatherCondition[] conditions = WeatherCondition.values();
    int randomConditionIndex = random.nextInt(conditions.length);
    return conditions[randomConditionIndex];
  }
}
