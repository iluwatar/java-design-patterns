package com.iluwatar.clientsideuicomposition;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiGateway class acts as a dynamic routing mechanism that forwards client
 * requests to the appropriate frontend components based on dynamically
 * registered routes.
 *
 * <p>This allows for flexible, runtime-defined routing without hardcoding specific paths.
 */
public class ApiGateway {

  // A map to store routes dynamically, where the key is the path and the value
  // is the associated FrontendComponent
  private final Map<String, FrontendComponent> routes = new HashMap<>();

  /**
   * Registers a route dynamically at runtime.
   *
   * @param path      the path to access the component (e.g., "/products")
   * @param component the frontend component to be accessed at the given path
   */
  public void registerRoute(String path, FrontendComponent component) {
    routes.put(path, component);
  }

  /**
   * Handles a client request by routing it to the appropriate frontend component.
   *
   * <p>This method dynamically handles parameters passed with the request, which
   * allows the frontend components to respond based on those parameters.
   *
   * @param path   the path for which the request is made (e.g., "/products", "/cart")
   * @param params a map of parameters that might influence the data fetching logic
   *               (e.g., filters, userId, categories, etc.)
   * @return the data fetched from the appropriate component or "404 Not Found"
   *         if the path is not registered
   */
  public String handleRequest(String path, Map<String, String> params) {
    if (routes.containsKey(path)) {
      // Fetch data dynamically based on the provided parameters
      return routes.get(path).fetchData(params);
    } else {
      // Return a 404 error if the path is not registered
      return "404 Not Found";
    }
  }
}
