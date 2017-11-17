package com.iluwatar.eip.splitter;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * It is very common in integration systems that incoming messages consists of many items bundled together. For example
 * an invoice document contains multiple invoice lines describing transaction (quantity, name of provided
 * service/sold goods, price etc.). Such bundled messages may not be accepted by other systems. This is where splitter
 * pattern comes in handy. It will take the whole document, split it based on given criteria and send individual
 * items to the endpoint.
 *
 * <p>
 * Splitter allows you to split messages based on defined criteria. It takes original message, process it and send
 * multiple parts to the output channel. It is not defined if it should keep the order of items though.
 * </p>
 *
 */
@SpringBootApplication
public class App {

  /**
   * Program entry point. It starts Spring Boot application and using Apache Camel it auto-configures routes.
   *
   * @param args command line args
   */
  public static void main(String[] args) throws Exception {
    // Run Spring Boot application and obtain ApplicationContext
    ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

    // Get CamelContext from ApplicationContext
    CamelContext camelContext = (CamelContext) context.getBean("camelContext");

    // Add a new routes that will handle endpoints form SplitterRoute class.
    camelContext.addRoutes(new RouteBuilder() {

      @Override
      public void configure() throws Exception {
        from("{{endpoint}}").log("ENDPOINT: ${body}");
      }

    });

    // Add producer that will send test message to an entry point in WireTapRoute
    String[] stringArray = {"Test item #1", "Test item #2", "Test item #3"};
    camelContext.createProducerTemplate().sendBody("{{entry}}", stringArray);

    SpringApplication.exit(context);
  }
}
