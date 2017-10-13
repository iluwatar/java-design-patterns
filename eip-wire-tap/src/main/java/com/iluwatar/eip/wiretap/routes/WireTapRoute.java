package com.iluwatar.eip.wiretap.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Sample wire tap route definition.
 *
 * <p>
 * It consumes messages out of the <i>direct:entry</i> entry point and forwards them to <i>direct:endpoint</i>.
 * Wire Tap intercepts the message and sends it to <i>direct:wireTap</i>, which in turn forwards it to
 * <i>direct:wireTapEndpoint</i>.
 * </p>
 *
 * In this example input/output endpoints names are stored in <i>application.properties</i> file.
 */
@Component
public class WireTapRoute extends RouteBuilder {

  /**
   * Configures the route
   * @throws Exception in case of exception during configuration
   */
  @Override
  public void configure() throws Exception {
    // Main route
    from("{{entry}}").wireTap("direct:wireTap").to("{{endpoint}}");

    // Wire tap route
    from("direct:wireTap").log("Message: ${body}").to("{{wireTapEndpoint}}");
  }
}
