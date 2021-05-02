/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.eip.message.channel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * When two applications communicate with each other using a messaging system they first need to
 * establish a communication channel that will carry the data. Message Channel decouples Message
 * producers and consumers.
 *
 * <p>The sending application doesn't necessarily know what particular application will end up
 * retrieving it, but it can be assured that the application that retrieves the information is
 * interested in that information. This is because the messaging system has different Message
 * Channels for different types of information the applications want to communicate. When an
 * application sends information, it doesn't randomly add the information to any channel available;
 * it adds it to a channel whose specific purpose is to communicate that sort of information.
 * Likewise, an application that wants to receive particular information doesn't pull info off some
 * random channel; it selects what channel to get information from based on what type of information
 * it wants.
 *
 * <p>In this example we use Apache Camel to establish two different Message Channels. The first
 * one reads from standard input and delivers messages to Direct endpoint. The second Message
 * Channel is established from the Direct component to console output. No actual messages are sent,
 * only the established routes are printed to standard output.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) throws Exception {
    var context = new DefaultCamelContext();

    context.addRoutes(new RouteBuilder() {

      @Override
      public void configure() throws Exception {
        from("stream:in").to("direct:greetings");
        from("direct:greetings").to("stream:out");
      }
    });

    context.start();
    context.getRoutes().forEach(r -> LOGGER.info(r.toString()));
    context.stop();
  }
}
