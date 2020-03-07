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

package com.iluwatar.eip.aggregator;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sometimes in enterprise systems there is a need to group incoming data in order to process it as
 * a whole. For example you may need to gather offers and after defined number of offers has been
 * received you would like to choose the one with the best parameters.
 *
 * <p>Aggregator allows you to merge messages based on defined criteria and parameters. It gathers
 * original messages, applies aggregation strategy and upon fulfilling given criteria, releasing
 * merged messages.
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

    // Add a new routes that will handle endpoints form SplitterRoute class.
    camelContext.addRoutes(new RouteBuilder() {
      @Override
      public void configure() {
        from("{{endpoint}}").log("ENDPOINT: ${body}");
      }
    });

    // Add producer that will send test message to an entry point in WireTapRoute
    String[] stringArray = {"Test item #1", "Test item #2", "Test item #3"};
    camelContext.createProducerTemplate().sendBody("{{entry}}", stringArray);

    SpringApplication.exit(context);
  }
}
