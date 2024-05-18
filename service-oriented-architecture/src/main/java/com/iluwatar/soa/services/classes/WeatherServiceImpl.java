package com.iluwatar.soa.services.classes;


import com.iluwatar.soa.model.WeatherCondition;
import java.util.Random;
import com.iluwatar.soa.services.interfaces.WeatherService;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
 final Random random = new Random();

  public WeatherCondition getCurrentWeather() {
    // Simulazione di una scelta casuale delle condizioni meteo
    WeatherCondition[] conditions = WeatherCondition.values();
    int index = random.nextInt(conditions.length);
    return conditions[index];
  }

}
