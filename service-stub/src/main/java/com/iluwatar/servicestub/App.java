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
