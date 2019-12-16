/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.eip.aggregator.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.jupiter.api.Test;

/**
 * Tests MessageAggregationStrategy
 */
public class MessageAggregationStrategyTest {

  @Test
  public void testAggregate() {
    var mas = new MessageAggregationStrategy();
    var oldExchange = new DefaultExchange((CamelContext) null);
    oldExchange.getIn().setBody("TEST1");

    var newExchange = new DefaultExchange((CamelContext) null);
    newExchange.getIn().setBody("TEST2");

    var output = mas.aggregate(oldExchange, newExchange);
    var outputBody = (String) output.getIn().getBody();
    assertEquals("TEST1;TEST2", outputBody);
  }

  @Test
  public void testAggregateOldNull() {
    var mas = new MessageAggregationStrategy();

    var newExchange = new DefaultExchange((CamelContext) null);
    newExchange.getIn().setBody("TEST2");

    var output = mas.aggregate(null, newExchange);
    var outputBody = (String) output.getIn().getBody();

    assertEquals(newExchange, output);
    assertEquals("TEST2", outputBody);
  }
}
