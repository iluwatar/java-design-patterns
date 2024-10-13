package com.iluwatar.clientsideuicomposition;

import java.util.Map;
import java.util.Random;

/**
 * FrontendComponent is an abstract class representing an independent frontend
 * component that fetches data dynamically based on the provided parameters.
 */
public abstract class FrontendComponent {

  public static final Random random = new Random();

  /**
   * Simulates asynchronous data fetching by introducing a random delay and
   * then fetching the data based on dynamic input.
   *
   * @param params a map of parameters that may affect the data fetching logic
   * @return the data fetched by the frontend component
   */
  public String fetchData(Map<String, String> params) {
    try {
      // Simulate delay in fetching data (e.g., network latency)
      Thread.sleep(random.nextInt(1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    // Fetch and return the data based on the given parameters
    return getData(params);
  }

  /**
   * Abstract method to be implemented by subclasses to return data based on
   * parameters.
   *
   * @param params a map of parameters that may affect the data fetching logic
   * @return the data for this specific component
   */
  protected abstract String getData(Map<String, String> params);
}
