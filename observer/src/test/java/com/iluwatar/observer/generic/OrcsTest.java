package com.iluwatar.observer.generic;

import com.iluwatar.observer.WeatherType;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Date: 12/27/15 - 12:07 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class OrcsTest extends ObserverTest<GOrcs> {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    final ArrayList<Object[]> testData = new ArrayList<>();
    testData.add(new Object[]{WeatherType.SUNNY, "The sun hurts the orcs' eyes."});
    testData.add(new Object[]{WeatherType.RAINY, "The orcs are dripping wet."});
    testData.add(new Object[]{WeatherType.WINDY, "The orc smell almost vanishes in the wind."});
    testData.add(new Object[]{WeatherType.COLD, "The orcs are freezing cold."});
    return testData;
  }

  /**
   * Create a new test with the given weather and expected response
   *
   * @param weather  The weather that should be unleashed on the observer
   * @param response The expected response from the observer
   */
  public OrcsTest(final WeatherType weather, final String response) {
    super(weather, response, GOrcs::new);
  }

}
