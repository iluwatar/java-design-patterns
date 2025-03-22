package com.iluwatar.servicestub;

public class StubSentimentAnalysisServer implements SentimentAnalysisServer {

  /**
   * Fake sentiment analyzer, always returns "Positive" if input string contains the word "good",
   * "Negative" if the string contains "bad" and "Neutral" otherwise.
   *
   * @param text the input string to analyze
   * @return sentiment classification result (Positive, Negative, or Neutral)
   */
  @Override
  public String analyzeSentiment(String text) {
    if (text.toLowerCase().contains("good")) {
      return "Positive";
    }
    else if (text.toLowerCase().contains("bad")) {
      return "Negative";
    }
    else {
      return "Neutral";
    }
  }
}
