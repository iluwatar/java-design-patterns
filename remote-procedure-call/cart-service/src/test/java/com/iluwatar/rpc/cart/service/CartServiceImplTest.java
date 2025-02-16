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
import com.iluwatar.rpc.proto.Shopping;
import com.iluwatar.rpc.proto.Shopping.ReduceProductRequest;
import com.iluwatar.rpc.proto.Shopping.ReduceProductResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
  @Mock
  private ShoppingServiceBlockingStub productServiceBlockingStub;
  @InjectMocks
  private CartServiceImpl cartService;

  private static List<Shopping.ProductResponse> products;

  @BeforeEach
  public void setUp() {
    products = new ArrayList<>();
    var product = productInCartProvider();
    products.add(Shopping.ProductResponse.newBuilder()
        .setId(product.getId())
        .setPrice(product.getPrice())
        .setType(product.getType())
        .setName(product.getName())
        .build());
  }

  @Test
  void testReduceCartQuantityFromProduct_NullProduct() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      cartService.reduceCartQuantityFromProduct(null);
    });
    assertEquals("Product state is null", exception.getMessage());
  }

  @Test
  void testReduceCartQuantityFromProduct_InvalidProductProperties() {
    ProductInCart invalidProduct = productInCartProvider();
    invalidProduct.setId(-1L);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      cartService.reduceCartQuantityFromProduct(invalidProduct);
    });
    assertEquals("Invalid Product id", exception.getMessage());
  }

  @Test
  void testReduceCartQuantityFromProduct_EmptyCart() {
    ProductInCart product = productInCartProvider();

    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      cartService.reduceCartQuantityFromProduct(product);
    });
    assertEquals("Products In cart array empty, populate cart first", exception.getMessage());
  }

  @Test
  void testReduceCartQuantityFromProduct_Success() {
    when(productServiceBlockingStub.getAllProducts(null)).thenReturn(products.iterator());

    ProductInCart product = productInCartProvider();
    product.setId(3L);

    ReduceProductResponse response =
        ReduceProductResponse.newBuilder().setStatus(true).setMessage("Success").build();
    when(productServiceBlockingStub.reduceProductQuantity(
        any(ReduceProductRequest.class))).thenReturn(response);

    cartService.getAllProducts();
    cartService.reduceCartQuantityFromProduct(product);
    verify(productServiceBlockingStub, times(1)).reduceProductQuantity(
        any(ReduceProductRequest.class));
  }

  @Test
  void testGetAllProducts_Success() {
    when(productServiceBlockingStub.getAllProducts(null)).thenReturn(products.iterator());

    List<Shopping.ProductResponse> products = new ArrayList<>();
    var productInCart = productInCartProvider();
    products.add(Shopping.ProductResponse.newBuilder()
        .setId(productInCart.getId())
        .setName(productInCart.getName())
        .setType(productInCart.getType())
        .setPrice(productInCart.getPrice() + 10)
        .build());

    when(productServiceBlockingStub.getAllProducts(null)).thenReturn(products.iterator());

    cartService.getAllProducts();
    assertDoesNotThrow(() -> cartService.getRandomProductFromCart());
  }

  @Test
  void testGetAllProducts_Exception() {
    when(productServiceBlockingStub.getAllProducts(null)).thenThrow(
        new RuntimeException("Service error"));

    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      cartService.getAllProducts();
    });
    assertEquals("Failed to fetch products from ProductService", exception.getMessage());
  }

  @Test
  void testGetRandomProductFromCart_EmptyCart() {
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      cartService.getRandomProductFromCart();
    });
    assertEquals("Products In cart array empty", exception.getMessage());
  }

  @Test
  void testGetRandomProductFromCart_Success() {
    when(productServiceBlockingStub.getAllProducts(null)).thenReturn(products.iterator());

    cartService.getAllProducts();
    ProductInCart randomProduct = cartService.getRandomProductFromCart();
    assertNotNull(randomProduct);
  }

  private ProductInCart productInCartProvider() {
    return ProductInCart.builder()
        .id(1L)
        .name("Test")
        .type("Test type")
        .price(0.0)
        .quantityToReduce(10)
        .build();
  }
}