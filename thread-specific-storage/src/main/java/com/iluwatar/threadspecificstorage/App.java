/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.threadspecificstorage;

import lombok.extern.slf4j.Slf4j;

/**
 * The Thread-Specific Storage pattern ensures that each thread has its own instance of a variable.
 * This is useful when multiple threads access the same code but must not share the same state or
 * data.
 *
 * <p>In this example, each hotel guest has a personal luggage record. Each guest (represented by a
 * thread) updates their personal luggage count. The values are stored in a ThreadLocal variable
 * ensuring thread isolation.
 *
 * <p>Key benefits demonstrated:
 *
 * <ul>
 *   <li>Thread safety without synchronization
 *   <li>No shared mutable state
 *   <li>Each task maintains its own independent context
 * </ul>
 */
@Slf4j
public class App {

  /**
   * Program main entry point.
   *
   * @param args program runtime arguments
   */
  public static void main(String[] args) {

    LOGGER.info("Hotel system starting using Thread-Specific Storage!");
    LOGGER.info("Each guest has a separate luggage count stored thread-locally.");

    Thread guest1 =
        new Thread(
            () -> {
              ThreadLocalContext.setUser("Guest-A");
              ThreadLocalContext.setLuggageCount(2);
              LOGGER.info(
                  "{} starts with {} luggage items.",
                  ThreadLocalContext.getUser(),
                  ThreadLocalContext.getLuggageCount());
              ThreadLocalContext.setLuggageCount(5);
              LOGGER.info(
                  "{} updated luggage count to {}.",
                  ThreadLocalContext.getUser(),
                  ThreadLocalContext.getLuggageCount());
              ThreadLocalContext.clear();
            });

    Thread guest2 =
        new Thread(
            () -> {
              ThreadLocalContext.setUser("Guest-B");
              ThreadLocalContext.setLuggageCount(1);
              LOGGER.info(
                  "{} starts with {} luggage items.",
                  ThreadLocalContext.getUser(),
                  ThreadLocalContext.getLuggageCount());
              ThreadLocalContext.setLuggageCount(3);
              LOGGER.info(
                  "{} updated luggage count to {}.",
                  ThreadLocalContext.getUser(),
                  ThreadLocalContext.getLuggageCount());
              ThreadLocalContext.clear();
            });

    guest1.start();
    guest2.start();

    try {
      guest1.join();
      guest2.join();
    } catch (InterruptedException e) {
      LOGGER.error("Thread interrupted", e);
    }

    LOGGER.info("All guest operations finished. System shutting down.");
  }
}
