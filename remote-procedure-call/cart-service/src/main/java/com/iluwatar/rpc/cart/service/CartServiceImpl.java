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
import com.iluwatar.rpc.proto.Shopping.ReduceProductRequest;
import com.iluwatar.rpc.proto.Shopping.ReduceProductResponse;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc.ShoppingServiceBlockingStub;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of cart-service Contract exposed by gRPC proto.
 * Thread safety is not guaranteed.
 * @link com.iluwatar.rpc.cart.model.ProductInCart
 * @link com.iluwatar.rpc.cart.service.CartService
 * @author CoderSleek
 * @version 1.0
 */
public class CartServiceImpl implements CartService {
  private final ShoppingServiceBlockingStub shoppingServiceBlockingStub;
  private final List<ProductInCart> productsInCart = new ArrayList<>();
  private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

  /**
   * Constructor to initialize CartServiceImpl with ProductServiceBlockingStub.
   * blocking-stub is initialized in Main
   * @param shoppingStub ProductServiceBlockingStub
   * @throws IllegalArgumentException if ProductServiceBlockingStub is null
   */
  public CartServiceImpl(ShoppingServiceBlockingStub shoppingStub) {
    if (shoppingStub == null) {
      throw new IllegalArgumentException("ShoppingServiceBlockingStub is null");
    }
    this.shoppingServiceBlockingStub = shoppingStub;
  }

  @Override
  public void reduceCartQuantityFromProduct(ProductInCart product)
      throws IllegalArgumentException, IllegalStateException {
    log.info("Started Request for reducing product quantity from cart in product-service");
    if (product == null) {
      throw new IllegalArgumentException("Product state is null");
    }
    if (product.getId() <= 0) {
      throw new IllegalArgumentException("Invalid Product id");
    }
    if (productsInCart.isEmpty()) {
      throw new IllegalStateException("Products In cart array empty, populate cart first");
    }

    // type exposed and maintained in gRPC proto
    ReduceProductRequest request = ReduceProductRequest
        .newBuilder()
        .setProductId(product.getId())
        .setQuantity(product.getQuantityToReduce())
        .build();

    log.info("Initiating RPC call to reduce quantity of product with id: {}", product.getId());
    ReduceProductResponse response = shoppingServiceBlockingStub.reduceProductQuantity(request);
    log.info("Completed RPC call reduce quantity with status: {}", response.getStatus());
    log.info("RPC call response message: {}", response.getMessage());
    log.info("Completed Request for reducing product quantity from cart in product-service");
  }

  @Override
  public void getAllProducts() {
    log.info("Started request to fetch all products from product-service");
    // stream of products / multiple products
    try {
      shoppingServiceBlockingStub.getAllProducts(null)
          .forEachRemaining(product ->
              productsInCart.add(ProductInCart.builder()
                  .name(product.getName())
                  .id(product.getId())
                  .price(product.getPrice())
                  .type(product.getType())
                  .quantityToReduce(0)
                  .build())
          );
    } catch (Exception e) {
      log.error("Error occurred while fetching products: ", e);
      throw new IllegalStateException("Failed to fetch products from ProductService", e);
    }
    log.info("Fetching of products completed");
  }

  /**
   * returns a random product from cart, if cart is empty throws IllegalStateException.
   * randomized just for demonstration purposes.
   * returns a new instance of ProductInCart to avoid mutation of quantityToReduce in original object.
   *
   * @return randomly chosen ProductInCart from List of ProductInCart
   * @throws IllegalStateException if cart is empty
   */
  @Override
  public ProductInCart getRandomProductFromCart() throws IllegalStateException {
    if (productsInCart.isEmpty()) {
      throw new IllegalStateException("Products In cart array empty");
    }

    // randomly choose a product for dynamic results
    int randInt = new Random().nextInt(productsInCart.size());
    // to builder is used to return a copy of the object to avoid mutation of original object
    return productsInCart.get(randInt).toBuilder().build();
  }
}
