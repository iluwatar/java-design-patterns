package com.iluwatar.eip.wiretap.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test class for <i>WireTapRoute</i>.
 * <p>
 * In order for it to work we have to mock endpoints we want to read/write to. To mock those we need to substitute
 * original endpoint names to mocks.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WireTapRouteTest.class)
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

    Message endpointIn = endpoint.getExchanges().get(0).getIn();
    Message wireTapEndpointIn = wireTapEndpoint.getExchanges().get(0).getIn();

    assertEquals("TEST", endpointIn.getBody());
    assertEquals("TEST", wireTapEndpointIn.getBody());
  }
}
