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
package com.iluwatar.rpc.product.service;

import com.iluwatar.rpc.product.mocks.ProductMocks;
import com.iluwatar.rpc.product.model.Product;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc.ShoppingServiceBlockingStub;
import com.iluwatar.rpc.proto.Shopping.ProductResponse;
import com.iluwatar.rpc.proto.Shopping.ReduceProductRequest;
import com.iluwatar.rpc.proto.Shopping.ReduceProductResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductServiceIntegrationTest {
  private static Server server;
  private static ManagedChannel channel;
  private static ShoppingServiceBlockingStub blockingStub;

  @BeforeAll
  public static void setup() throws IOException {
    List<Product> products = ProductMocks.getMockProducts();
    var productServiceMain = new ProductServiceImpl(products);

    server = ServerBuilder
        .forPort(8080)
        .addService(new ProductServiceGrpcImpl(productServiceMain))
        .build()
        .start();

    channel = ManagedChannelBuilder.forAddress("localhost", 8080)
        .usePlaintext()
        .build();

    blockingStub = ShoppingServiceGrpc.newBlockingStub(channel);
  }

  @AfterAll
  public static void teardown() {
    channel.shutdownNow();
    server.shutdownNow();
  }

  @Test
  void testReduceProductQuantity() {
    ReduceProductRequest request = ReduceProductRequest.newBuilder()
        .setProductId(1L)
        .setQuantity(5)
        .build();

    ReduceProductResponse response = blockingStub.reduceProductQuantity(request);

    assertTrue(response.getStatus());
    assertEquals("Success", response.getMessage());
  }

  @Test
  void testGetAllProducts() {
    var responseIterator = blockingStub.getAllProducts(null);

    ArrayList<ProductResponse> responses = new ArrayList<>();
    responseIterator.forEachRemaining(responses::add);

    assertEquals(ProductMocks.getMockProducts().size(), responses.size());
  }
}
