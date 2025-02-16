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
package com.iluwatar.rpc.cart;

import com.iluwatar.rpc.cart.service.CartServiceImpl;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc;
import com.iluwatar.rpc.proto.ShoppingServiceGrpc.ShoppingServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the cart service.
 * Initializes the shopping channel and the cart service.
 *
 * @author CoderSleek
 * @version 1.0
 */
public class Main {
  private static final int SERVER_PORT = 8080;
  private static final String HOST = "localhost";
  private static final Logger log = LoggerFactory.getLogger(Main.class);
  /**
   * Main method to initialize the cart service and channel for RPC connection
   * initializes blocking stub and passes it to CartServiceImpl for constructor initialization.
   * Initializes data fetching from product-service.
   * shuts down after 1 request
   */
  public static void main(String[] args) {
    ManagedChannel productChannel = ManagedChannelBuilder
        .forAddress(HOST, SERVER_PORT)
        .usePlaintext()
        .enableRetry()
        .keepAliveTime(10, TimeUnit.SECONDS)
        .build();

    ShoppingServiceBlockingStub blockingStub = ShoppingServiceGrpc.newBlockingStub(productChannel);
    log.info("cart-service started");

    var cartService = new CartServiceImpl(blockingStub);
    cartService.getAllProducts();

    var productInCart = cartService.getRandomProductFromCart();
    productInCart.setQuantityToReduce(10);

    cartService.reduceCartQuantityFromProduct(productInCart);
    productChannel.shutdown();
    log.info("cart-service execution successful, shutting down");
  }
}