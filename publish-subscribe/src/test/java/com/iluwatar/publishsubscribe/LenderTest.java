package com.iluwatar.publishsubscribe;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LenderTest {

  private Lender lender;
  private static BrokerService broker;

  @BeforeAll
  static void setUp() throws Exception {
    broker = new BrokerService();
    broker.setPersistent(false);
    broker.start();
  }

  @AfterAll
  static void tearDown() throws Exception {
    broker.stop();
  }

  @Test
  void testTLenderConstructor() {
    //act
    lender = new Lender("TopicCF", "RateTopic");

    //assert
    Assertions.assertNotNull(lender.gettConnection());
    Assertions.assertNotNull(lender.getTopic());
    Assertions.assertNotNull(lender.gettSession());
  }

  @Test
  void textExit() {
    //assemble
    lender = new Lender("TopicCF", "RateTopic");

    //act
    boolean isClosed = lender.close();

    //assert
    Assertions.assertTrue(isClosed);
  }
}
