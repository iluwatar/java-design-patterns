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

package com.iluwatar.eip.wiretap;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * In most integration cases there is a need to monitor the messages flowing through the system. It
 * is usually achieved by intercepting the message and redirecting it to a different location like
 * console, filesystem or the database. It is important that such functionality should not modify
 * the original message and influence the processing path.
 *
 * <p>
 * Wire Tap allows you to route messages to a separate location while they are being forwarded to
 * the ultimate destination. It basically consumes messages of the input channel and publishes the
 * unmodified message to both output channels.
 * </p>
 */
@SpringBootApplication
public class App {

  /**
   * Program entry point. It starts Spring Boot application and using Apache Camel it
   * auto-configures routes.
   *
   * @param args command line args
   */
  public static void main(String[] args) throws Exception {
    // Run Spring Boot application and obtain ApplicationContext
    var context = SpringApplication.run(App.class, args);

    // Get CamelContext from ApplicationContext
    var camelContext = (CamelContext) context.getBean("camelContext");

    // Add a new routes that will handle endpoints form WireTapRoute class.
    camelContext.addRoutes(new RouteBuilder() {

      @Override
      public void configure() throws Exception {
        from("{{endpoint}}").log("ENDPOINT: ${body}");
        from("{{wireTapEndpoint}}").log("WIRETAPPED ENDPOINT: ${body}");
      }

    });

    // Add producer that will send test message to an entry point in WireTapRoute
    camelContext.createProducerTemplate().sendBody("{{entry}}", "Test message");

    SpringApplication.exit(context);
  }
}
