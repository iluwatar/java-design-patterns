package com.iluwatar.eip.aggregator.routes;

import org.apache.camel.EndpointInject;
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
 * Test class for <i>AggregatorRoute</i>.
 * <p>
 * In order for it to work we have to mock endpoints we want to read/write to. To mock those we need to substitute
 * original endpoint names to mocks.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AggregatorRouteTest.class)
@ActiveProfiles("test")
@EnableAutoConfiguration
@ComponentScan
public class AggregatorRouteTest {

  @EndpointInject(uri = "{{entry}}")
  private ProducerTemplate entry;

  @EndpointInject(uri = "{{endpoint}}")
  private MockEndpoint endpoint;

  /**
   * Test if endpoint receives three separate messages.
   * @throws Exception in case of en exception during the test
   */
  @Test
  @DirtiesContext
  public void testSplitter() throws Exception {

    // Three items in one entry message
    entry.sendBody("TEST1");
    entry.sendBody("TEST2");
    entry.sendBody("TEST3");
    entry.sendBody("TEST4");
    entry.sendBody("TEST5");

    // Endpoint should have three different messages in the end order of the messages is not important
    endpoint.expectedMessageCount(2);
    endpoint.assertIsSatisfied();

    String body = (String) endpoint.getReceivedExchanges().get(0).getIn().getBody();
    assertEquals(3, body.split(";").length);

    String body2 = (String) endpoint.getReceivedExchanges().get(1).getIn().getBody();
    assertEquals(2, body2.split(";").length);
  }
}
