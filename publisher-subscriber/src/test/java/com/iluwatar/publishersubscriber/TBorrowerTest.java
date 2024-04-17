package com.iluwatar.publishersubscriber;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TBorrowerTest {

  private TBorrower tBorrower;

  void setUp() throws Exception {
    BrokerService broker = new BrokerService();
    broker.setPersistent(false);
    broker.start();
  }

  @Test
  void testTBorrowerConstructor() {
    //act
    tBorrower = new TBorrower("TopicCF", "RateTopic", 6.0); //Arbitrary rate

    //assert
    Assertions.assertNotNull(tBorrower.gettConnection());
    Assertions.assertNotNull(tBorrower.getTopic());
    Assertions.assertNotNull(tBorrower.gettSession());
  }
}
