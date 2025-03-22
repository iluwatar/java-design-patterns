package com.iluwatar.servicestub;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Real implementation of SentimentAnalysisServer.
 * Simulates random sentiment classification with processing delay.
 */

public class RealSentimentAnalysisServer implements SentimentAnalysisServer {
  /**
   * A real sentiment analysis implementation would analyze the input string using, e.g., NLP and
   * determine whether the sentiment is positive, negative or neutral. Here we simply choose a random
   * number to simulate this. The "model" may take some time to process the input and we simulate
   * this by delaying the execution 5 seconds.
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

