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
package com.iluwatar.servicestub;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Real implementation of SentimentAnalysisServer. Simulates random sentiment classification with
 * processing delay.
 */
public class RealSentimentAnalysisServer implements SentimentAnalysisServer {
  /**
   * A real sentiment analysis implementation would analyze the input string using, e.g., NLP and
   * determine whether the sentiment is positive, negative or neutral. Here we simply choose a
   * random number to simulate this. The "model" may take some time to process the input and we
   * simulate this by delaying the execution 5 seconds.
   *
   * @param text the input string to analyze
   * @return sentiment classification result (Positive, Negative, or Neutral)
   */
  private final Supplier<Integer> sentimentSupplier;

  // Constructor
  public RealSentimentAnalysisServer(Supplier<Integer> sentimentSupplier) {
    this.sentimentSupplier = sentimentSupplier;
  }

  @SuppressWarnings("java:S2245") // Safe use: Randomness is for simulation/testing only
  public RealSentimentAnalysisServer() {
    this(() -> new Random().nextInt(3));
  }

  @Override
  public String analyzeSentiment(String text) {
    int sentiment = sentimentSupplier.get();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return sentiment == 0 ? "Positive" : sentiment == 1 ? "Negative" : "Neutral";
  }
}
