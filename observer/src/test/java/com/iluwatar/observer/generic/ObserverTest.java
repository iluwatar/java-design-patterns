package com.iluwatar.observer.generic;

import com.iluwatar.observer.StdOutTest;
import com.iluwatar.observer.WeatherType;

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
public abstract class ObserverTest<O extends Observer> extends StdOutTest {

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
  ObserverTest(final WeatherType weather, final String response, final Supplier<O> factory) {
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

    observer.update(null, this.weather);
    verify(getStdOutMock()).println(this.response);
    verifyNoMoreInteractions(getStdOutMock());
  }

}