/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sample aggregator route definition.
 *
 * <p>
 * It consumes messages out of the <i>direct:entry</i> entry point and forwards them to <i>direct:endpoint</i>.
 * Route accepts messages containing String as a body, it aggregates the messages based on the settings and forwards
 * them as CSV to the output chanel.
 *
 * Settings for the aggregation are: aggregate until 3 messages are bundled or wait 2000ms before sending bundled
 * messages further.
 * </p>
 *
 * In this example input/output endpoints names are stored in <i>application.properties</i> file.
 */
@Component
public class AggregatorRoute extends RouteBuilder {

  @Autowired
  private MessageAggregationStrategy aggregator;

  /**
   * Configures the route
   * @throws Exception in case of exception during configuration
   */
  @Override
  public void configure() throws Exception {
    // Main route
    from("{{entry}}").aggregate(constant(true), aggregator)
        .completionSize(3).completionInterval(2000)
        .to("{{endpoint}}");
  }
}
