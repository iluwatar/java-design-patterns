/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.doubledispatch;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * When a message with a parameter is sent to an object, the resultant behaviour is defined by the
 * implementation of that method in the receiver. Sometimes the behaviour must also be determined by
 * the type of the parameter.
 *
 * <p>One way to implement this would be to create multiple instanceof-checks for the methods
 * parameter. However, this creates a maintenance issue. When new types are added we would also need
 * to change the method's implementation and add a new instanceof-check. This violates the single
 * responsibility principle - a class should have only one reason to change.
 *
 * <p>Instead of the instanceof-checks a better way is to make another virtual call on the
 * parameter object. This way new functionality can be easily added without the need to modify
 * existing implementation (open-closed principle).
 *
 * <p>In this example we have hierarchy of objects ({@link GameObject}) that can collide to each
 * other. Each object has its own coordinates which are checked against the other objects'
 * coordinates. If there is an overlap, then the objects collide utilizing the Double Dispatch
 * pattern.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // initialize game objects and print their status
    var objects = List.of(
        new FlamingAsteroid(0, 0, 5, 5),
        new SpaceStationMir(1, 1, 2, 2),
        new Meteoroid(10, 10, 15, 15),
        new SpaceStationIss(12, 12, 14, 14)
    );
    objects.forEach(o -> LOGGER.info(o.toString()));
    LOGGER.info("");

    // collision check
    objects.forEach(o1 -> objects.forEach(o2 -> {
      if (o1 != o2 && o1.intersectsWith(o2)) {
        o1.collision(o2);
      }
    }));
    LOGGER.info("");

    // output eventual object statuses
    objects.forEach(o -> LOGGER.info(o.toString()));
    LOGGER.info("");
  }
}
