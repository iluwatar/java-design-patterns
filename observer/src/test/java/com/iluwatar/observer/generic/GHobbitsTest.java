package com.iluwatar.observer.generic;

import com.iluwatar.observer.Hobbits;
import com.iluwatar.observer.WeatherObserverTest;
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
public class GHobbitsTest extends ObserverTest<GHobbits> {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    final ArrayList<Object[]> testData = new ArrayList<>();
    testData.add(new Object[]{WeatherType.SUNNY, "The happy hobbits bade in the warm sun."});
    testData.add(new Object[]{WeatherType.RAINY, "The hobbits look for cover from the rain."});
    testData.add(new Object[]{WeatherType.WINDY, "The hobbits hold their hats tightly in the windy weather."});
    testData.add(new Object[]{WeatherType.COLD, "The hobbits are shivering in the cold weather."});
    return testData;
  }

  /**
   * Create a new test with the given weather and expected response
   *
   * @param weather  The weather that should be unleashed on the observer
   * @param response The expected response from the observer
   */
  public GHobbitsTest(final WeatherType weather, final String response) {
    super(weather, response, GHobbits::new);
  }

}
