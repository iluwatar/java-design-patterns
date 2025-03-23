package com.iluwatar.servicestub;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StubSentimentAnalysisServerTest {

  private final StubSentimentAnalysisServer stub = new StubSentimentAnalysisServer();

  @Test
  void testPositiveSentiment() {
    String result = stub.analyzeSentiment("This is a good product");
    assertEquals("Positive", result);
  }

  @Test
  void testNegativeSentiment() {
    String result = stub.analyzeSentiment("This is a bad product");
    assertEquals("Negative", result);
  }

  @Test
  void testNeutralSentiment() {
    String result = stub.analyzeSentiment("This product is average");
    assertEquals("Neutral", result);
  }
}
