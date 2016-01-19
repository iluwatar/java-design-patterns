package com.iluwatar.publish.subscribe;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * There are well-established patterns for implementing broadcasting. The Observer pattern describes
 * the need to decouple observers from their subject (that is, the originator of the event) so that
 * the subject can easily provide event notification to all interested observers no matter how many
 * observers there are (even none). The Publish-Subscribe pattern expands upon Observer by adding
 * the notion of an event channel for communicating event notifications.
 * <p>
 * A Publish-Subscribe Channel works like this: It has one input channel that splits into multiple
 * output channels, one for each subscriber. When an event is published into the channel, the
 * Publish-Subscribe Channel delivers a copy of the message to each of the output channels. Each
 * output end of the channel has only one subscriber, which is allowed to consume a message only
 * once. In this way, each subscriber gets the message only once, and consumed copies disappear from
 * their channels.
 * <p>
 * In this example we use Apache Camel to establish a Publish-Subscribe Channel from "direct-origin"
 * to "mock:foo", "mock:bar" and "stream:out".
 * 
 */
public class App {

  /**
   * Program entry point
   */
  public static void main(String[] args) throws Exception {
    CamelContext context = new DefaultCamelContext();
    context.addRoutes(new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:origin").multicast().to("mock:foo", "mock:bar", "stream:out");
      }
    });
    ProducerTemplate template = context.createProducerTemplate();
    context.start();
    context.getRoutes().stream().forEach((r) -> System.out.println(r));
    template.sendBody("direct:origin", "Hello from origin");
    context.stop();
  }
}
