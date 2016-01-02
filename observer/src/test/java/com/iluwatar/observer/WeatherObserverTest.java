package com.iluwatar.observer;

import org.junit.Test;

import java.util.function.Supplier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/27/15 - 11:44 AM
 *
 * @author Jeroen Meulemeester
 */
public abstract class WeatherObserverTest<O extends WeatherObserver> extends StdOutTest {

  /**
   * The observer instance factory
   */
  private final Supplier<O> factory;

  /**
   * The weather type currently tested
   */
  private final WeatherType weather;

  /**
   * The expected response from the observer
   */
  private final String response;

  /**
   * Create a new test instance using the given parameters
   *
   * @param weather  The weather currently being tested
   * @param response The expected response from the observer
   * @param factory  The factory, used to create an instance of the tested observer
   */
  WeatherObserverTest(final WeatherType weather, final String response, final Supplier<O> factory) {
    this.weather = weather;
    this.response = response;
    this.factory = factory;
  }

  /**
   * Verify if the weather has the expected influence on the observer
   */
  @Test
  public void testObserver() {
    final O observer = this.factory.get();
    verifyZeroInteractions(getStdOutMock());

    observer.update(this.weather);
    verify(getStdOutMock()).println(this.response);
    verifyNoMoreInteractions(getStdOutMock());
  }

}