package com.iluwatar.soa.services;


import com.iluwatar.soa.model.WeatherCondition;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WeatherService {

  public WeatherCondition getCurrentWeather() {
    // Simulazione di una scelta casuale delle condizioni meteo
    WeatherCondition[] conditions = WeatherCondition.values();
    Random random = new Random();
    int index = random.nextInt(conditions.length);
    return conditions[index];
  }

}
