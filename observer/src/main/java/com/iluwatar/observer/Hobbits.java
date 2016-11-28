/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Hobbits
 *
 */
public class Hobbits implements WeatherObserver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Hobbits.class);

  @Override
  public void update(WeatherType currentWeather) {
    switch (currentWeather) {
      case COLD:
        LOGGER.info("The hobbits are shivering in the cold weather.");
        break;
      case RAINY:
        LOGGER.info("The hobbits look for cover from the rain.");
        break;
      case SUNNY:
        LOGGER.info("The happy hobbits bade in the warm sun.");
        break;
      case WINDY:
        LOGGER.info("The hobbits hold their hats tightly in the windy weather.");
        break;
      default:
        break;
    }
  }
}
