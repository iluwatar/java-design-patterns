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
import com.iluwatar.rpc.proto.Shopping.Empty;
import com.iluwatar.rpc.proto.Shopping.ProductResponse;
import com.iluwatar.rpc.proto.Shopping.ReduceProductRequest;
import com.iluwatar.rpc.proto.Shopping.ReduceProductResponse;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceGrpcImplTest {
  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductServiceGrpcImpl productServiceGrpcImpl;

  private List<Product> productsMockData;

  @BeforeEach
  public void setUp() {
    productsMockData = ProductMocks.getMockProducts();
  }

  @Test
  void testReduceProductQuantity_ProductNotFound() {
    var request = ReduceProductRequest.newBuilder()
        .setProductId(6L)
        .setQuantity(5)
        .build();
    StreamObserver<ReduceProductResponse> responseObserver = mock(StreamObserver.class);

    when(productService.getProduct(request.getProductId())).thenReturn(Optional.empty());

    productServiceGrpcImpl.reduceProductQuantity(request, responseObserver);

    ArgumentCaptor<ReduceProductResponse> responseCaptor =
        ArgumentCaptor.forClass(ReduceProductResponse.class);
    verify(responseObserver).onNext(responseCaptor.capture());
    verify(responseObserver).onCompleted();

    ReduceProductResponse response = responseCaptor.getValue();
    assertFalse(response.getStatus());
    assertEquals("Product with ID does not exist", response.getMessage());
  }

  @Test
  void testReduceProductQuantity_InvalidQuantity() {
    Product product = productsMockData.get(0);
    ReduceProductRequest request = ReduceProductRequest.newBuilder()
        .setProductId(product.getId())
        .setQuantity(-5)
        .build();
    StreamObserver<ReduceProductResponse> responseObserver = mock(StreamObserver.class);

    when(productService.getProduct(product.getId())).thenReturn(Optional.of(product));

    productServiceGrpcImpl.reduceProductQuantity(request, responseObserver);

    ArgumentCaptor<ReduceProductResponse> responseCaptor =
        ArgumentCaptor.forClass(ReduceProductResponse.class);
    verify(responseObserver).onNext(responseCaptor.capture());
    verify(responseObserver).onCompleted();

    ReduceProductResponse response = responseCaptor.getValue();
    assertFalse(response.getStatus());
    assertEquals("Invalid Quantity", response.getMessage());
  }

  @Test
  void testReduceProductQuantity_InsufficientQuantity() {
    Product product = productsMockData.get(0);

    ReduceProductRequest request = ReduceProductRequest.newBuilder()
        .setProductId(product.getId())
        .setQuantity(1000)
        .build();
    StreamObserver<ReduceProductResponse> responseObserver = mock(StreamObserver.class);

    when(productService.getProduct(product.getId())).thenReturn(Optional.of(product));

    productServiceGrpcImpl.reduceProductQuantity(request, responseObserver);

    ArgumentCaptor<ReduceProductResponse> responseCaptor =
        ArgumentCaptor.forClass(ReduceProductResponse.class);
    verify(responseObserver).onNext(responseCaptor.capture());
    verify(responseObserver).onCompleted();

    ReduceProductResponse response = responseCaptor.getValue();
    assertFalse(response.getStatus());
    assertEquals("Product has less quantity in stock than requested", response.getMessage());
  }

  @Test
  void testReduceProductQuantity_Success() {
    Product product = productsMockData.get(0);
    ReduceProductRequest request = ReduceProductRequest.newBuilder()
        .setProductId(product.getId())
        .setQuantity(5)
        .build();
    StreamObserver<ReduceProductResponse> responseObserver = mock(StreamObserver.class);

    when(productService.getProduct(product.getId())).thenReturn(Optional.of(product));

    productServiceGrpcImpl.reduceProductQuantity(request, responseObserver);

    ArgumentCaptor<ReduceProductResponse> responseCaptor =
        ArgumentCaptor.forClass(ReduceProductResponse.class);
    verify(responseObserver).onNext(responseCaptor.capture());
    verify(responseObserver).onCompleted();

    ReduceProductResponse response = responseCaptor.getValue();
    assertTrue(response.getStatus());
    assertEquals("Success", response.getMessage());
    assertEquals(5, product.getQuantity());
  }

  @Test
  void testGetAllProducts_Success() {
    List<Product> productsLocal = ProductMocks.getMockProducts();
    var product = productsLocal.get(0);

    StreamObserver<ProductResponse> responseObserver = mock(StreamObserver.class);

    when(productService.getAllProducts()).thenReturn(productsMockData);
    productServiceGrpcImpl.getAllProducts(null, responseObserver);

    ArgumentCaptor<ProductResponse> responseCaptor = ArgumentCaptor.forClass(ProductResponse.class);
    verify(responseObserver, times(5)).onNext(responseCaptor.capture());
    verify(responseObserver).onCompleted();

    assertEquals(productsLocal.size(), responseCaptor.getAllValues().size());
    assertEquals(Long.compare(product.getId(), responseCaptor.getAllValues().get(0).getId()), 0);
    assertEquals(product.getName(), responseCaptor.getAllValues().get(0).getName());
    assertEquals(product.getType(), responseCaptor.getAllValues().get(0).getType());
    assertEquals(
        Double.compare(product.getPrice(), responseCaptor.getAllValues().get(0).getPrice()), 0);
  }

  @Test
  void testGetAllProducts_Failure() {
    StreamObserver<ProductResponse> responseObserver = mock(StreamObserver.class);

    when(productService.getAllProducts()).thenThrow(new IllegalStateException("Database error"));

    productServiceGrpcImpl.getAllProducts(Empty.newBuilder().build(), responseObserver);

    verify(responseObserver).onError(any(StatusException.class));
    verify(responseObserver).onCompleted();
  }
}
