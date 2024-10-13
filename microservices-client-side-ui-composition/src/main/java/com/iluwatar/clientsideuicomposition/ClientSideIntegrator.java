package com.iluwatar.clientsideuicomposition;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * ClientSideIntegrator class simulates the client-side integration layer that
 * dynamically assembles various frontend components into a cohesive user
 * interface.
 */
@Slf4j
public class ClientSideIntegrator {

  private final ApiGateway apiGateway;

  /**
   * Constructor that accepts an instance of ApiGateway to handle dynamic
   * routing.
   *
   * @param apiGateway the gateway that routes requests to different frontend
   *     components
   */
  public ClientSideIntegrator(ApiGateway apiGateway) {
    this.apiGateway = apiGateway;
  }

  /**
   * Composes the user interface dynamically by fetching data from different
   * frontend components based on provided parameters.
   *
   * @param path the route of the frontend component
   * @param params a map of dynamic parameters to influence the data fetching
   */
  public void composeUi(String path, Map<String, String> params) {
    // Fetch data dynamically based on the route and parameters
    String data = apiGateway.handleRequest(path, params);
    LOGGER.info("Composed UI Component for path '" + path + "':");
    LOGGER.info(data);
  }
}
