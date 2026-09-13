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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * Coordinates the three phases of the pattern.
 *
 * <ol>
 *   <li><b>Scatter</b>: the same request is sent to every provider concurrently.
 *   <li><b>Gather</b>: replies are collected until each one has either arrived, failed, or exceeded
 *       the timeout. Late and failed replies are logged and dropped so a single slow provider
 *       cannot hold up the whole answer. Dropping a reply also cancels the provider call, so a
 *       timed-out provider is interrupted and its pool thread is freed instead of staying busy with
 *       work nobody will read.
 *   <li><b>Aggregate</b>: the gathered replies are reduced by an {@link Aggregator}.
 * </ol>
 *
 * <p>The class owns the executor it was given and shuts it down on {@link #close()}.
 */
@Slf4j
public class ScatterGather implements AutoCloseable {

  /**
   * A reply that is still in flight after the scatter phase.
   *
   * @param provider the provider the request was sent to
   * @param reply the future that completes with the provider's quote, or exceptionally
   */
  public record PendingReply(RateProvider provider, CompletableFuture<RateQuote> reply) {}

  private static final Duration DEFAULT_SHUTDOWN_GRACE = Duration.ofSeconds(1);

  private final ExecutorService executor;
  private final Duration timeout;
  private final Duration shutdownGrace;

  /**
   * Creates a coordinator that gives the executor one second to terminate on {@link #close()}.
   *
   * @param executor runs the calls to the providers; it is shut down when this object is closed
   * @param timeout how long after the scatter each reply has to arrive; the timer starts when the
   *     request is scattered, not when gather is called
   */
  public ScatterGather(ExecutorService executor, Duration timeout) {
    this(executor, timeout, DEFAULT_SHUTDOWN_GRACE);
  }

  /**
   * Creates a coordinator with an explicit shutdown grace period, so tests that close a coordinator
   * whose task ignores interrupts do not have to wait out the default one.
   *
   * @param executor runs the calls to the providers; it is shut down when this object is closed
   * @param timeout how long after the scatter each reply has to arrive
   * @param shutdownGrace how long {@link #close()} waits for the executor to terminate
   */
  ScatterGather(ExecutorService executor, Duration timeout, Duration shutdownGrace) {
    this.executor = executor;
    this.timeout = timeout;
    this.shutdownGrace = shutdownGrace;
  }

  /**
   * Scatter phase: sends the request to every provider without waiting for any reply.
   *
   * @param request the request to broadcast
   * @param providers the recipients
   * @return one pending reply per provider, in the same order as the providers
   */
  public List<PendingReply> scatter(RateRequest request, List<RateProvider> providers) {
    LOGGER.info(
        "Scattering request for {} nights in {} to {} providers",
        request.nights(),
        request.city(),
        providers.size());
    var pending = new ArrayList<PendingReply>();
    for (var provider : providers) {
      var reply = new CompletableFuture<RateQuote>();
      var task =
          executor.submit(
              () -> {
                try {
                  reply.complete(provider.quote(request));
                } catch (RuntimeException e) {
                  reply.completeExceptionally(e);
                }
              });
      // A reply that times out or is cancelled also cancels its task, interrupting the provider
      // call so that the pool thread is handed back instead of finishing work nobody will read.
      reply
          .orTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
          .whenComplete(
              (quote, failure) -> {
                if (failure != null && !task.isDone()) {
                  task.cancel(true);
                }
              });
      pending.add(new PendingReply(provider, reply));
    }
    return pending;
  }

  /**
   * Gather phase: waits until every pending reply has settled and keeps the successful ones.
   *
   * @param pending the replies produced by {@link #scatter}
   * @return the quotes that arrived in time, possibly fewer than the number of providers
   */
  public List<RateQuote> gather(List<PendingReply> pending) {
    CompletableFuture.allOf(
            pending.stream().map(PendingReply::reply).toArray(CompletableFuture[]::new))
        .exceptionally(ex -> null)
        .join();
    var quotes = new ArrayList<RateQuote>();
    for (var entry : pending) {
      try {
        var quote = entry.reply().join();
        LOGGER.info("Gathered quote {} from {}", quote.total(), entry.provider().name());
        quotes.add(quote);
      } catch (CompletionException e) {
        if (e.getCause() instanceof TimeoutException) {
          LOGGER.warn(
              "Dropping {}: no reply within {} ms", entry.provider().name(), timeout.toMillis());
        } else {
          LOGGER.warn("Dropping {}: {}", entry.provider().name(), e.getCause().getMessage());
        }
      } catch (CancellationException e) {
        LOGGER.warn("Dropping {}: the reply was cancelled", entry.provider().name());
      }
    }
    LOGGER.info("Gathered {} of {} replies", quotes.size(), pending.size());
    return quotes;
  }

  /**
   * Runs all three phases: scatter, gather, and aggregate.
   *
   * @param request the request to broadcast
   * @param providers the recipients
   * @param aggregator reduces the gathered quotes
   * @param <R> the aggregated result type
   * @return the aggregated result
   */
  public <R> R scatterGather(
      RateRequest request, List<RateProvider> providers, Aggregator<R> aggregator) {
    return aggregator.aggregate(gather(scatter(request, providers)));
  }

  /** Stops the executor, interrupting providers that are still working on a dropped request. */
  @Override
  public void close() {
    executor.shutdownNow();
    try {
      if (!executor.awaitTermination(shutdownGrace.toMillis(), TimeUnit.MILLISECONDS)) {
        LOGGER.warn("Executor did not terminate within {} ms", shutdownGrace.toMillis());
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
