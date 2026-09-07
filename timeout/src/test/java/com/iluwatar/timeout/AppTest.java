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
package com.iluwatar.timeout;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;

class AppTest {

  private static final TimeoutPolicy GENEROUS = TimeoutPolicy.of("catalog", 5_000);
  private static final TimeoutPolicy STRICT = TimeoutPolicy.of("catalog", 50);
  private static final List<String> FALLBACK = List.of("Webcam");

  @Test
  void shouldLaunchApp() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }

  @Test
  void shouldBeInstantiable() {
    assertNotNull(new App(), "App should be instantiable");
  }

  @Test
  void returnsTheResponseOfAServiceThatAnswersWithinItsLimit() {
    var service = new DownstreamService("catalog", Duration.ofMillis(1), List.of("Laptop"));

    try (var executor = new TimeoutExecutor()) {
      assertEquals(List.of("Laptop"), App.call(executor, GENEROUS, service, FALLBACK));
    }
  }

  @Test
  void returnsTheFallbackWhenTheServiceExceedsItsLimit() {
    var service = new DownstreamService("catalog", Duration.ofSeconds(60), List.of("Laptop"));

    try (var executor = new TimeoutExecutor()) {
      assertEquals(FALLBACK, App.call(executor, STRICT, service, FALLBACK));
    }
  }
}
