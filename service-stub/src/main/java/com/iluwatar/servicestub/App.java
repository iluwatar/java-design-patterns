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

import lombok.extern.slf4j.Slf4j;

/**
 * A Service Stub is a dummy implementation of an external service used during development or
 * testing. The purpose is to provide a lightweight "stub" when the real service may not always be
 * available (or too slow to use during testing).
 *
 * <p>This implementation simulates a simple sentiment analysis program, where a text is analyzed to
 * deduce whether it is a positive, negative or neutral sentiment. The stub returns a response based
 * on whether the analyzed text contains the words "good" or "bad", not accounting for stopwords or
 * the underlying semantic of the text.
 *
 * <p>The "real" sentiment analysis class simulates the processing time for the request by pausing
 * the execution of the thread for 5 seconds. In the stub sentiment analysis class the response is
 * immediate. In addition, the stub returns a deterministic output with regard to the input. This
 * is extra useful for testing purposes.
 */


@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    LOGGER.info("Setting up the real sentiment analysis server.");
    RealSentimentAnalysisServer realSentimentAnalysisServer = new RealSentimentAnalysisServer();
    String text = "This movie is soso";
    LOGGER.info("Analyzing input: {}", text);
    String sentiment = realSentimentAnalysisServer.analyzeSentiment(text);
    LOGGER.info("The sentiment is: {}", sentiment);

    LOGGER.info("Setting up the stub sentiment analysis server.");
    StubSentimentAnalysisServer stubSentimentAnalysisServer = new StubSentimentAnalysisServer();
    text = "This movie is so bad";
    LOGGER.info("Analyzing input: {}", text);
    sentiment = stubSentimentAnalysisServer.analyzeSentiment(text);
    LOGGER.info("The sentiment is: {}", sentiment);

  }
}
