package com.iluwatar.eip.aggregator.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests MessageAggregationStrategy
 */
public class MessageAggregationStrategyTest {

  @Test
  public void testAggregate() {
    MessageAggregationStrategy mas = new MessageAggregationStrategy();
    Exchange oldExchange = new DefaultExchange((CamelContext) null);
    oldExchange.getIn().setBody("TEST1");

    Exchange newExchange = new DefaultExchange((CamelContext) null);
    newExchange.getIn().setBody("TEST2");

    Exchange output = mas.aggregate(oldExchange, newExchange);
    String outputBody = (String) output.getIn().getBody();
    assertEquals("TEST1;TEST2", outputBody);
  }

  @Test
  public void testAggregateOldNull() {
    MessageAggregationStrategy mas = new MessageAggregationStrategy();

    Exchange newExchange = new DefaultExchange((CamelContext) null);
    newExchange.getIn().setBody("TEST2");

    Exchange output = mas.aggregate(null, newExchange);
    String outputBody = (String) output.getIn().getBody();

    assertEquals(newExchange, output);
    assertEquals("TEST2", outputBody);
  }
}
