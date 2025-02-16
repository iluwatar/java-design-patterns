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

import com.iluwatar.rpc.product.model.Product;
import com.iluwatar.rpc.proto.Shopping.Empty;
import com.iluwatar.rpc.proto.Shopping.ProductResponse;
import com.iluwatar.rpc.proto.Shopping.ReduceProductRequest;
import com.iluwatar.rpc.proto.Shopping.ReduceProductResponse;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc.ShoppingServiceImplBase;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of product-service Contract exposed by gRPC proto.
 * Thread safety is not guaranteed.
 *
 * @link com.iluwatar.rpc.product.service.ProductService
 * @link com.iluwatar.rpc.product.model.Product
 * @author CoderSleek
 * @version 1.0
 */
public class ProductServiceGrpcImpl extends ShoppingServiceImplBase {
  private final ProductService productService;
  private static final Logger log = LoggerFactory.getLogger(ProductServiceGrpcImpl.class);

  /**
   * Constructor for initializing Implementation of ProductService Contract internally.
   * ProductService Implementation stores Product data in internal state for simplicity.
   *
   * @param productService ProductService
   */
  public ProductServiceGrpcImpl(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Reduces the quantity of a specified product via product id by the requested quantity.
   * To simulate a customer buying an item, once bought the quantity of the product is reduced from stock.
   * @param request contains product id and quantity to reduce
   * @param responseStreamObserver for iterating over response
   */
  @Override
  public void reduceProductQuantity(ReduceProductRequest request,
                                    StreamObserver<ReduceProductResponse> responseStreamObserver) {
    log.info("Received request to reduce product quantity");
    boolean status;
    String message;
    var product = this.productService.getProduct(request.getProductId());

    if (product.isEmpty()) {
      status = false;
      message = "Product with ID does not exist";
    } else {
      int productQuantity = product.get().getQuantity();
      int requestedQuantity = request.getQuantity();

      if (requestedQuantity <= 0) {
        status = false;
        message = "Invalid Quantity";
      } else if (requestedQuantity > productQuantity) {
        status = false;
        message = "Product has less quantity in stock than requested";
      } else {
        log.info("Before reducing quantity: {}", productQuantity);
        product.get().setQuantity(productQuantity - requestedQuantity);
        log.info("After reducing quantity: {}", product.get().getQuantity());
        status = true;
        message = "Success";
      }
    }

    var response = ReduceProductResponse
        .newBuilder()
        .setMessage(message)
        .setStatus(status)
        .build();

    responseStreamObserver.onNext(response);
    responseStreamObserver.onCompleted();
    log.info("Request to Reduce Product Quantity Execution Completed");
  }

  /**
   * Fetches all products from ProductService and streams them to the client.
   * Throws NOT_FOUND status exception if products are not found.
   * @param request empty placeholder request
   * @param responseStreamObserver for iterating over responses
   */
  @Override
  public void getAllProducts(Empty request,
                             StreamObserver<ProductResponse> responseStreamObserver) {
    log.info("Received request to fetch all products");
    List<Product> products;

    try {
      products = this.productService.getAllProducts();
    } catch (IllegalStateException e) {
      log.error("Failed to fetch products from ProductService", e);
      responseStreamObserver.onError(
          Status.NOT_FOUND
              .withDescription(e.getMessage())
              .asException());
      responseStreamObserver.onCompleted();
      return;
    }

    for (var product : products) {
      responseStreamObserver.onNext(
          ProductResponse.newBuilder()
              .setId(product.getId())
              .setName(product.getName())
              .setPrice(product.getPrice())
              .setType(product.getType())
              .build()
      );
    }

    responseStreamObserver.onCompleted();
    log.info("Request to fetch all products Execution Completed");
  }
}
