package com.iluwatar.servicestub;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class RealSentimentAnalysisServerTest {

  @Test
  void testPositiveSentiment() {
    RealSentimentAnalysisServer server = new RealSentimentAnalysisServer(() -> 0);
    assertEquals("Positive", server.analyzeSentiment("Test"));
  }

  @Test
  void testNegativeSentiment() {
    RealSentimentAnalysisServer server = new RealSentimentAnalysisServer(() -> 1);
    assertEquals("Negative", server.analyzeSentiment("Test"));
  }

  @Test
  void testNeutralSentiment() {
    RealSentimentAnalysisServer server = new RealSentimentAnalysisServer(() -> 2);
    assertEquals("Neutral", server.analyzeSentiment("Test"));
  }

}
