package com.iluwatar.eip.aggregator.routes;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

/**
 * Aggregation strategy joining bodies of messages. If message is first one <i>oldMessage</i> is null. All changes are
 * made on IN messages.
 */
@Component
public class MessageAggregationStrategy implements AggregationStrategy {

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    if (oldExchange == null) {
      return newExchange;
    }

    String in1 = (String) oldExchange.getIn().getBody();
    String in2 = (String) newExchange.getIn().getBody();

    oldExchange.getIn().setBody(in1 + ";" + in2);

    return oldExchange;
  }
}
