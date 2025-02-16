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
package com.iluwatar.rpc.cart.service;

import com.iluwatar.rpc.cart.model.ProductInCart;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc.ShoppingServiceBlockingStub;
import com.iluwatar.rpc.proto.Shopping.ProductResponse;
import com.iluwatar.rpc.proto.Shopping.ReduceProductRequest;
import com.iluwatar.rpc.proto.Shopping.ReduceProductResponse;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplIntegrationTest {

  @Mock
  private ShoppingServiceBlockingStub productServiceBlockingStub;

  @InjectMocks
  private CartServiceImpl cartService;

  private ProductResponse product;

  @BeforeEach
  public void setUp() {
    product = ProductResponse.newBuilder()
        .setId(1)
        .setName("Test")
        .setPrice(100)
        .setType("Test")
        .build();

    ArrayList<ProductResponse> productList = new ArrayList<>();

    productList.add(product);
    when(productServiceBlockingStub.getAllProducts(null)).thenReturn(productList.iterator());
    cartService.getAllProducts();
  }

  @Test
  void testGetRandomProductFromCartAndReduceQuantity_Success() {
    ProductInCart randomProduct = cartService.getRandomProductFromCart();

    assertNotNull(randomProduct);
    assertEquals(product.getId(), randomProduct.getId());
    assertEquals(product.getName(), randomProduct.getName());
    randomProduct.setQuantityToReduce(100);

    ReduceProductResponse response = ReduceProductResponse.newBuilder()
        .setStatus(true)
        .setMessage("Success")
        .build();

    when(productServiceBlockingStub.reduceProductQuantity(
        any(ReduceProductRequest.class)
    )).thenReturn(response);
    cartService.reduceCartQuantityFromProduct(randomProduct);

    verify(productServiceBlockingStub, times(1)).reduceProductQuantity(
        any(ReduceProductRequest.class));
  }
}