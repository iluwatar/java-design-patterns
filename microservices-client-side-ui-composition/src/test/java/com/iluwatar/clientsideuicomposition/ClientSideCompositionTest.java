package com.iluwatar.clientsideuicomposition;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ClientSideCompositionTest contains unit tests to validate dynamic route registration and UI composition.
 */
class ClientSideCompositionTest {

  /**
   * Tests dynamic registration of frontend components and dynamic composition of UI.
   */
  @Test
  void testClientSideUIComposition() {
    // Create API Gateway and dynamically register frontend components
    ApiGateway apiGateway = new ApiGateway();
    apiGateway.registerRoute("/products", new ProductFrontend());
    apiGateway.registerRoute("/cart", new CartFrontend());

    // Create the Client-Side Integrator
    ClientSideIntegrator integrator = new ClientSideIntegrator(apiGateway);

    // Dynamically pass parameters for data fetching
    Map<String, String> productParams = new HashMap<>();
    productParams.put("category", "electronics");

    // Compose UI for products and cart with dynamic params
    integrator.composeUi("/products", productParams);

    Map<String, String> cartParams = new HashMap<>();
    cartParams.put("userId", "user123");
    integrator.composeUi("/cart", cartParams);

    // Validate the dynamically fetched data
    String productData = apiGateway.handleRequest("/products", productParams);
    String cartData = apiGateway.handleRequest("/cart", cartParams);

    assertTrue(productData.contains("Product List for category 'electronics'"));
    assertTrue(cartData.contains("Shopping Cart for user 'user123'"));
  }
}
