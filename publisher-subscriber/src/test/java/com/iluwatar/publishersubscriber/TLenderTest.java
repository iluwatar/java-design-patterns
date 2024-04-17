package com.iluwatar.publishersubscriber;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;

public class TLenderTest {

  private TLender tLender;

  void setUp() throws Exception {
    BrokerService broker = new BrokerService();
    broker.setPersistent(false);
    broker.start();
  }

  @Test
  void testTLenderConstructor() {
    //act
    tLender = new TLender("TopicCF", "RateTopic");

    //assert
    Assertions.assertNotNull(tLender.gettConnection());
    Assertions.assertNotNull(tLender.getTopic());
    Assertions.assertNotNull(tLender.gettSession());
  }
}
