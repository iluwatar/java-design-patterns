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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScatterGatherTest {

  private static final RateRequest REQUEST = new RateRequest("Porto", LocalDate.of(2026, 5, 1), 2);
  private static final Duration TIMEOUT = Duration.ofMillis(300);
  private static final Duration SHORT_SHUTDOWN_GRACE = Duration.ofMillis(50);

  private final CountDownLatch gate = new CountDownLatch(1);
  private ExecutorService executor;
  private ScatterGather scatterGather;

  @BeforeEach
  void setUp() {
    executor = Executors.newFixedThreadPool(4);
    scatterGather = new ScatterGather(executor, TIMEOUT);
  }

  @AfterEach
  void tearDown() {
    gate.countDown();
    scatterGather.close();
  }

  @Test
  void shouldGatherEveryReplyWhenAllProvidersAnswer() {
    var providers = List.<RateProvider>of(provider("A", "120.00"), provider("B", "80.00"));

    var quotes = scatterGather.gather(scatterGather.scatter(REQUEST, providers));

    assertEquals(2, quotes.size());
    assertEquals(new RateQuote("A", new BigDecimal("240.00")), quotes.get(0));
    assertEquals(new RateQuote("B", new BigDecimal("160.00")), quotes.get(1));
  }

  @Test
  void shouldDropProviderThatMissesTheTimeout() {
    var providers = List.<RateProvider>of(provider("fast", "100.00"), blockedProvider("stuck"));

    var quotes = scatterGather.gather(scatterGather.scatter(REQUEST, providers));

    assertEquals(List.of(new RateQuote("fast", new BigDecimal("200.00"))), quotes);
  }

  @Test
  void shouldInterruptProviderThatMissesTheTimeoutAndFreeItsThread() throws Exception {
    var interrupted = new CountDownLatch(1);
    var slow =
        new RateProvider() {
          @Override
          public String name() {
            return "slow";
          }

          @Override
          public RateQuote quote(RateRequest request) {
            try {
              gate.await();
            } catch (InterruptedException e) {
              interrupted.countDown();
              Thread.currentThread().interrupt();
              throw new IllegalStateException("interrupted", e);
            }
            return new RateQuote(name(), BigDecimal.ONE);
          }
        };
    var ownExecutor = Executors.newSingleThreadExecutor();
    try (var subject = new ScatterGather(ownExecutor, TIMEOUT)) {
      assertTrue(subject.gather(subject.scatter(REQUEST, List.of(slow))).isEmpty());

      assertTrue(interrupted.await(1, TimeUnit.SECONDS), "timed-out provider was not interrupted");
      // the only pool thread is free again, so a fresh call answers well within the timeout
      var quotes = subject.gather(subject.scatter(REQUEST, List.of(provider("fast", "100.00"))));
      assertEquals(List.of(new RateQuote("fast", new BigDecimal("200.00"))), quotes);
    }
  }

  @Test
  void shouldDropProviderThatFails() {
    var providers = List.<RateProvider>of(new FailingRateProvider("down"), provider("up", "50.00"));

    var quotes = scatterGather.gather(scatterGather.scatter(REQUEST, providers));

    assertEquals(List.of(new RateQuote("up", new BigDecimal("100.00"))), quotes);
  }

  @Test
  void shouldDropProviderWhoseReplyWasCancelled() {
    var providers = List.<RateProvider>of(blockedProvider("stuck"), provider("up", "50.00"));
    var pending = scatterGather.scatter(REQUEST, providers);
    pending.get(0).reply().cancel(true);

    var quotes = scatterGather.gather(pending);

    assertEquals(List.of(new RateQuote("up", new BigDecimal("100.00"))), quotes);
  }

  @Test
  void shouldAggregateCheapestQuote() {
    var providers =
        List.<RateProvider>of(
            provider("pricey", "300.00"), provider("cheap", "90.00"), provider("mid", "150.00"));

    var best = scatterGather.scatterGather(REQUEST, providers, Aggregator.cheapestQuote());

    assertTrue(best.isPresent());
    assertEquals("cheap", best.get().provider());
    assertEquals(new BigDecimal("180.00"), best.get().total());
  }

  @Test
  void shouldReturnEmptyResultWhenNoProviderAnswers() {
    var providers =
        List.<RateProvider>of(new FailingRateProvider("down"), blockedProvider("stuck"));

    var best = scatterGather.scatterGather(REQUEST, providers, Aggregator.cheapestQuote());

    assertTrue(best.isEmpty());
  }

  @Test
  void shouldShutDownExecutorOnClose() {
    scatterGather.scatter(REQUEST, List.of(blockedProvider("stuck")));

    scatterGather.close();

    assertTrue(executor.isShutdown());
    assertTrue(executor.isTerminated());
  }

  @Test
  void shouldPreserveInterruptFlagWhenCloseIsInterrupted() {
    var stubborn = new CountDownLatch(1);
    var ownExecutor = Executors.newSingleThreadExecutor();
    var subject = new ScatterGather(ownExecutor, Duration.ofSeconds(10));
    subject.scatter(REQUEST, List.of(interruptIgnoringProvider("stubborn", stubborn)));
    try {
      Thread.currentThread().interrupt();

      subject.close();

      assertTrue(Thread.interrupted(), "close must re-set the interrupt flag it swallowed");
      assertTrue(ownExecutor.isShutdown());
    } finally {
      stubborn.countDown();
    }
  }

  @Test
  void shouldReturnFromCloseWhenTaskIgnoresInterrupts() {
    var stubborn = new CountDownLatch(1);
    var ownExecutor = Executors.newSingleThreadExecutor();
    var subject = new ScatterGather(ownExecutor, Duration.ofSeconds(10), SHORT_SHUTDOWN_GRACE);
    subject.scatter(REQUEST, List.of(interruptIgnoringProvider("stubborn", stubborn)));
    try {
      subject.close();

      assertTrue(ownExecutor.isShutdown());
      assertFalse(ownExecutor.isTerminated(), "the stubborn task is still running after close");
    } finally {
      stubborn.countDown();
    }
  }

  private static RateProvider provider(String name, String nightlyRate) {
    return new InMemoryRateProvider(name, new BigDecimal(nightlyRate));
  }

  /** A provider that does not answer until the test releases the gate. */
  private RateProvider blockedProvider(String name) {
    return new RateProvider() {
      @Override
      public String name() {
        return name;
      }

      @Override
      public RateQuote quote(RateRequest request) {
        try {
          gate.await();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new IllegalStateException("interrupted", e);
        }
        return new RateQuote(name, BigDecimal.ONE);
      }
    };
  }

  /** A provider that keeps waiting on the latch even when its thread is interrupted. */
  private static RateProvider interruptIgnoringProvider(String name, CountDownLatch latch) {
    return new RateProvider() {
      @Override
      public String name() {
        return name;
      }

      @Override
      public RateQuote quote(RateRequest request) {
        while (true) {
          try {
            latch.await();
            return new RateQuote(name, BigDecimal.ONE);
          } catch (InterruptedException ignored) {
            // deliberately keeps waiting to simulate a task that does not honour interrupts
          }
        }
      }
    };
  }
}
