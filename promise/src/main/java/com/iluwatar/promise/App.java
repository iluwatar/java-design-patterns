/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * <p>The Promise object is used for asynchronous computations. A Promise represents an operation that
 * hasn't completed yet, but is expected in the future.
 * 
 * <p>A Promise represents a proxy for a value not necessarily known when the promise is created. It 
 * allows you to associate dependent promises to an asynchronous action's eventual success value or 
 * failure reason. This lets asynchronous methods return values like synchronous methods: instead of the final
 * value, the asynchronous method returns a promise of having a value at some point in the future.
 * 
 * <p>Promises provide a few advantages over callback objects:
 * <ul>
 * <li> Functional composition and error handling
 * <li> Prevents callback hell and provides callback aggregation
 * </ul>
 * 
 * <p>
 * 
 * @see CompletableFuture
 */
public class App {

  private App() {
  }
  
  /**
   * Program entry point
   * @param args arguments
   * @throws InterruptedException if main thread is interrupted.
   * @throws ExecutionException if an execution error occurs.
   */
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try {
      promiseUsage(executor);
    } finally {
      executor.shutdownNow();
    }
  }

  private static void promiseUsage(Executor executor)
      throws InterruptedException, ExecutionException {
    String urlString = "https://raw.githubusercontent.com/iluwatar/java-design-patterns/Promise/promise/README.md";
    Promise<Integer> lineCountPromise = new Promise<String>().fulfillInAsync(() -> {
      return downloadFile(urlString);
    }, executor).then(fileLocation -> {
      return countLines(fileLocation);
    });
    
    Promise<Map<Character, Integer>> charFrequencyPromise = new Promise<String>().fulfillInAsync(() -> {
      return String.valueOf(downloadFile(urlString));
    }, executor).then(fileLocation -> {
      return characterFrequency(fileLocation);
    });
    
    lineCountPromise.get();
    System.out.println("Line count is: " + lineCountPromise.get());
    charFrequencyPromise.get();
    System.out.println("Char frequency is: " + charFrequencyPromise.get());
  }

  private static Map<Character, Integer> characterFrequency(String fileLocation) {
    // TODO Auto-generated method stub
    return null;
  }

  private static Integer countLines(String fileLocation) {
    int lineCount = 0;
    try (Reader reader = new FileReader(fileLocation); 
        BufferedReader bufferedReader = new BufferedReader(reader);) {
      for (String line; (line = bufferedReader.readLine()) != null; ) {
        lineCount++;
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return lineCount;
  }

  private static String downloadFile(String urlString) throws InterruptedException, IOException {
    URL url = new URL(urlString);
    File file = File.createTempFile("promise_pattern", null);
    try (Reader reader = new InputStreamReader(url.openStream()); 
        BufferedReader bufferedReader = new BufferedReader(reader);
        FileWriter writer = new FileWriter(file)) {
      for (String line; (line = bufferedReader.readLine()) != null; ) {
        writer.write(line);
        writer.write("\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    System.out.println("File downloaded at: " + file.getAbsolutePath());
    return file.getAbsolutePath();
  }
}
