package com.iluwatar.publishsubscribe;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

public class BorrowerTest {

  private Borrower borrower;
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
  void testTBorrowerConstructor() {
    //act
    borrower = new Borrower("TopicCF", "RateTopic", 6.0); //Arbitrary rate

    //assert
    Assertions.assertNotNull(borrower.gettConnection());
    Assertions.assertNotNull(borrower.getTopic());
    Assertions.assertNotNull(borrower.gettSession());
  }

  @Test
  void testOnMessage() throws JMSException {
    //assemble
    borrower = new Borrower("TopicCF", "RateTopic", 6.0);
    BytesMessage msg = borrower.gettSession().createBytesMessage();
    msg.writeDouble(5);
    msg.reset();

    //act
    borrower.onMessage(msg);

    //assert
    Assertions.assertEquals(borrower.getNewRate(), 5);
  }

  @Test
  void testClose() {
    //assemble
    borrower = new Borrower("TopicCF", "RateTopic", 6.0);

    //act
    boolean isClosed = borrower.close();

    //assert
    Assertions.assertTrue(isClosed);
  }
}
