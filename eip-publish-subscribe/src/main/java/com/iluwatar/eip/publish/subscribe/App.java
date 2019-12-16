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

package com.iluwatar.eip.publish.subscribe;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * There are well-established patterns for implementing broadcasting. The Observer pattern describes
 * the need to decouple observers from their subject (that is, the originator of the event) so that
 * the subject can easily provide event notification to all interested observers no matter how many
 * observers there are (even none). The Publish-Subscribe pattern expands upon Observer by adding
 * the notion of an event channel for communicating event notifications.
 *
 * <p>A Publish-Subscribe Channel works like this: It has one input channel that splits into
 * multiple output channels, one for each subscriber. When an event is published into the channel,
 * the Publish-Subscribe Channel delivers a copy of the message to each of the output channels. Each
 * output end of the channel has only one subscriber, which is allowed to consume a message only
 * once. In this way, each subscriber gets the message only once, and consumed copies disappear from
 * their channels.
 *
 * <p>In this example we use Apache Camel to establish a Publish-Subscribe Channel from
 * "direct-origin" to "mock:foo", "mock:bar" and "stream:out".
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   */
  public static void main(String[] args) throws Exception {
    var context = new DefaultCamelContext();
    context.addRoutes(new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:origin").multicast().to("mock:foo", "mock:bar", "stream:out");
      }
    });
    var template = context.createProducerTemplate();
    context.start();
    context.getRoutes().forEach(r -> LOGGER.info(r.toString()));
    template.sendBody("direct:origin", "Hello from origin");
    context.stop();
  }
}
