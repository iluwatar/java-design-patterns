package com.iluwatar.servicestub;

/**
 * Sentiment analysis server interface to be implemented by sentiment analysis services.
 */

public interface SentimentAnalysisServer {
  /**
   * Analyzes the sentiment of the input text and returns the result.
   *
   * @param text the input text to analyze
   * @return sentiment classification result
   */
  String analyzeSentiment(String text);
}
