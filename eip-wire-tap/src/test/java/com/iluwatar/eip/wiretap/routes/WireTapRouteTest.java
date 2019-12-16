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

package com.iluwatar.eip.wiretap.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test class for <i>WireTapRoute</i>.
 * <p>
 * In order for it to work we have to mock endpoints we want to read/write to. To mock those we need
 * to substitute original endpoint names to mocks.
 * </p>
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WireTapRouteTest.class)
@ActiveProfiles("test")
@EnableAutoConfiguration
@ComponentScan
public class WireTapRouteTest {

  @EndpointInject(uri = "{{entry}}")
  private ProducerTemplate entry;

  @EndpointInject(uri = "{{endpoint}}")
  private MockEndpoint endpoint;

  @EndpointInject(uri = "{{wireTapEndpoint}}")
  private MockEndpoint wireTapEndpoint;

  /**
   * Test if both endpoints receive exactly one message containing the same, unchanged body.
   *
   * @throws Exception in case of en exception during the test
   */
  @Test
  @DirtiesContext
  public void testWireTap() throws Exception {
    entry.sendBody("TEST");

    endpoint.expectedMessageCount(1);
    wireTapEndpoint.expectedMessageCount(1);

    endpoint.assertIsSatisfied();
    wireTapEndpoint.assertIsSatisfied();

    var endpointIn = endpoint.getExchanges().get(0).getIn();
    var wireTapEndpointIn = wireTapEndpoint.getExchanges().get(0).getIn();

    assertEquals("TEST", endpointIn.getBody());
    assertEquals("TEST", wireTapEndpointIn.getBody());
  }
}
