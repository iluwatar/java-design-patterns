package com.iluwatar.eip.splitter.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Sample splitter route definition.
 *
 * <p>
 * </p>
 *
 * In this example input/output endpoints names are stored in <i>application.properties</i> file.
 */
@Component
public class SplitterRoute extends RouteBuilder {

  /**
   * Configures the route
   * @throws Exception in case of exception during configuration
   */
  @Override
  public void configure() throws Exception {
    // Main route
    from("{{entry}}").split().body().to("{{endpoint}}");
  }
}
