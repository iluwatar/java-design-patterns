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
package com.iluwatar.logaggregation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogAggregatorTest {

  @Mock
  private CentralLogStore centralLogStore;
  private LogAggregator logAggregator;

  @BeforeEach
  void setUp() {
    logAggregator = new LogAggregator(centralLogStore, LogLevel.INFO);
  }

  @Test
  void whenThreeInfoLogsAreCollected_thenCentralLogStoreShouldStoreAllOfThem() {
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 1"));
    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 2"));

    verifyNoInteractionsWithCentralLogStore();

    logAggregator.collectLog(createLogEntry(LogLevel.INFO, "Sample log message 3"));

    verifyCentralLogStoreInvokedTimes(3);
  }

  @Test
  void whenDebugLogIsCollected_thenNoLogsShouldBeStored() {
    logAggregator.collectLog(createLogEntry(LogLevel.DEBUG, "Sample debug log message"));

    verifyNoInteractionsWithCentralLogStore();
  }

  private static LogEntry createLogEntry(LogLevel logLevel, String message) {
    return new LogEntry("ServiceA", logLevel, message, LocalDateTime.now());
  }

  private void verifyNoInteractionsWithCentralLogStore() {
    verify(centralLogStore, times(0)).storeLog(any());
  }

  private void verifyCentralLogStoreInvokedTimes(int times) {
    verify(centralLogStore, times(times)).storeLog(any());
  }
}
