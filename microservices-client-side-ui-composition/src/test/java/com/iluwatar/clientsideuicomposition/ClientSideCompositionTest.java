/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.clientsideuicomposition;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * ClientSideCompositionTest contains unit tests to validate dynamic route registration and UI
 * composition.
 */
class ClientSideCompositionTest {

  /** Tests dynamic registration of frontend components and dynamic composition of UI. */
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
