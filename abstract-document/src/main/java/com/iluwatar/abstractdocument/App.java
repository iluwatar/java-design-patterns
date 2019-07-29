/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.abstractdocument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iluwatar.abstractdocument.domain.Car;
import com.iluwatar.abstractdocument.domain.enums.Property;

/**
 * The Abstract Document pattern enables handling additional, non-static
 * properties. This pattern uses concept of traits to enable type safety and
 * separate properties of different classes into set of interfaces.
 * <p>
 * <p>
 * In Abstract Document pattern,({@link AbstractDocument}) fully implements
 * {@link Document}) interface. Traits are then defined to enable access to
 * properties in usual, static way.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Executes the App
   */
  public App() {
    LOGGER.info("Constructing parts and car");

    Map<String, Object> carProperties = new HashMap<>();
    carProperties.put(Property.MODEL.toString(), "300SL");
    carProperties.put(Property.PRICE.toString(), 10000L);

    Map<String, Object> wheelProperties = new HashMap<>();
    wheelProperties.put(Property.TYPE.toString(), "wheel");
    wheelProperties.put(Property.MODEL.toString(), "15C");
    wheelProperties.put(Property.PRICE.toString(), 100L);

    Map<String, Object> doorProperties = new HashMap<>();
    doorProperties.put(Property.TYPE.toString(), "door");
    doorProperties.put(Property.MODEL.toString(), "Lambo");
    doorProperties.put(Property.PRICE.toString(), 300L);

    carProperties.put(Property.PARTS.toString(), Arrays.asList(wheelProperties, doorProperties));

    Car car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().get());
    LOGGER.info("-> price: {}", car.getPrice().get());
    LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}", p.getType().get(), p.getModel().get(), p.getPrice().get()));
  }

  /**
   * Program entry point
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    new App();
  }

}
