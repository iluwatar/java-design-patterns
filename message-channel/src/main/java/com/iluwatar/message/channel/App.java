package com.iluwatar.message.channel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * When two applications communicate with each other using a messaging system they first need to
 * establish a communication channel that will carry the data. Message Channel decouples Message
 * producers and consumers.
 * <p>
 * The sending application doesn't necessarily know what particular application will end up
 * retrieving it, but it can be assured that the application that retrieves the information is
 * interested in that information. This is because the messaging system has different Message
 * Channels for different types of information the applications want to communicate. When an
 * application sends information, it doesn't randomly add the information to any channel available;
 * it adds it to a channel whose specific purpose is to communicate that sort of information.
 * Likewise, an application that wants to receive particular information doesn't pull info off some
 * random channel; it selects what channel to get information from based on what type of information
 * it wants.
 * <p>
 * In this example we use Apache Camel to establish two different Message Channels. The first one
 * reads from standard input and delivers messages to Direct endpoint. The second Message Channel is
 * established from the Direct component to console output. No actual messages are sent, only the
 * established routes are printed to standard output.
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
        from("stream:in").to("direct:greetings");
        from("direct:greetings").to("stream:out");
      }
    });

    context.start();
    context.getRoutes().stream().forEach((r) -> System.out.println(r));
    context.stop();
  }
}
