/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.promise;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Promise object is used for asynchronous computations. A Promise represents an operation
 * that hasn't completed yet, but is expected in the future.
 *
 * <p>A Promise represents a proxy for a value not necessarily known when the promise is created. It
 * allows you to associate dependent promises to an asynchronous action's eventual success value or
 * failure reason. This lets asynchronous methods return values like synchronous methods: instead 
 * of the final value, the asynchronous method returns a promise of having a value at some point 
 * in the future.
 *
 * <p>Promises provide a few advantages over callback objects:
 * <ul>
 * <li> Functional composition and error handling
 * <li> Prevents callback hell and provides callback aggregation
 * </ul>
 *
 * <p>In this application the usage of promise is demonstrated with two examples:
 * <ul>
 * <li>Count Lines: In this example a file is downloaded and its line count is calculated.
 * The calculated line count is then consumed and printed on console.
 * <li>Lowest Character Frequency: In this example a file is downloaded and its lowest frequency
 * character is found and printed on console. This happens via a chain of promises, we start with
 * a file download promise, then a promise of character frequency, then a promise of lowest
 * frequency character which is finally consumed and result is printed on console.
 * </ul>
 * 
 * @see CompletableFuture
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final String DEFAULT_URL = "https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md";
  private final ExecutorService executor;
  private final CountDownLatch stopLatch;

  private App() {
    executor = Executors.newFixedThreadPool(2);
    stopLatch = new CountDownLatch(2);
  }

  /**
   * Program entry point.
   * @param args arguments
   * @throws InterruptedException if main thread is interrupted.
   * @throws ExecutionException if an execution error occurs.
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    App app = new App();
    try {
      app.promiseUsage();
    } finally {
      app.stop();
    }
  }

  private void promiseUsage() {
    calculateLineCount();

    calculateLowestFrequencyChar();
  }

  /*
   * Calculate the lowest frequency character and when that promise is fulfilled,
   * consume the result in a Consumer<Character>
   */
  private void calculateLowestFrequencyChar() {
    lowestFrequencyChar()
        .thenAccept(
          charFrequency -> {
            LOGGER.info("Char with lowest frequency is: {}", charFrequency);
            taskCompleted();
          }
      );
  }

  /*
   * Calculate the line count and when that promise is fulfilled, consume the result
   * in a Consumer<Integer>
   */
  private void calculateLineCount() {
    countLines()
        .thenAccept(
          count -> {
            LOGGER.info("Line count is: {}", count);
            taskCompleted();
          }
      );
  }

  /*
   * Calculate the character frequency of a file and when that promise is fulfilled,
   * then promise to apply function to calculate lowest character frequency.
   */
  private Promise<Character> lowestFrequencyChar() {
    return characterFrequency()
        .thenApply(Utility::lowestFrequencyChar);
  }

  /*
   * Download the file at DEFAULT_URL and when that promise is fulfilled,
   * then promise to apply function to calculate character frequency.
   */
  private Promise<Map<Character, Integer>> characterFrequency() {
    return download(DEFAULT_URL)
        .thenApply(Utility::characterFrequency);
  }

  /*
   * Download the file at DEFAULT_URL and when that promise is fulfilled,
   * then promise to apply function to count lines in that file.
   */
  private Promise<Integer> countLines() {
    return download(DEFAULT_URL)
        .thenApply(Utility::countLines);
  }

  /*
   * Return a promise to provide the local absolute path of the file downloaded in background.
   * This is an async method and does not wait until the file is downloaded.
   */
  private Promise<String> download(String urlString) {
    return new Promise<String>()
        .fulfillInAsync(
            () -> Utility.downloadFile(urlString), executor)
        .onError(
            throwable -> {
              throwable.printStackTrace();
              taskCompleted();
            }
        );
  }

  private void stop() throws InterruptedException {
    stopLatch.await();
    executor.shutdownNow();
  }

  private void taskCompleted() {
    stopLatch.countDown();
  }
}
