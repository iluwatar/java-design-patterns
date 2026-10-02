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
package com.iluwatar.scattergather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RateProviderTest {

  private static final RateRequest REQUEST =
      new RateRequest("Madrid", LocalDate.of(2026, 3, 10), 4);

  @Test
  void inMemoryProviderMultipliesNightlyRateByNights() {
    var provider = new InMemoryRateProvider("inn", new BigDecimal("25.50"));

    assertEquals("inn", provider.name());
    assertEquals(new RateQuote("inn", new BigDecimal("102.00")), provider.quote(REQUEST));
  }

  @Test
  void delayedProviderDelegatesAfterWaiting() {
    var provider =
        new DelayedRateProvider(
            new InMemoryRateProvider("slow", new BigDecimal("10.00")), Duration.ofMillis(10));

    assertEquals("slow", provider.name());
    assertEquals(new RateQuote("slow", new BigDecimal("40.00")), provider.quote(REQUEST));
  }

  @Test
  void failingProviderThrows() {
    var provider = new FailingRateProvider("down");

    assertEquals("down", provider.name());
    assertThrows(IllegalStateException.class, () -> provider.quote(REQUEST));
  }

  @Test
  void requestRejectsNonPositiveNights() {
    assertThrows(
        IllegalArgumentException.class, () -> new RateRequest("Rome", LocalDate.of(2026, 1, 1), 0));
  }
}
