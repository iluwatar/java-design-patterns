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
package com.iluwatar.rpc.product;

import com.iluwatar.rpc.product.mocks.ProductMocks;
import com.iluwatar.rpc.product.model.Product;
import com.iluwatar.rpc.product.service.ProductServiceGrpcImpl;
import com.iluwatar.rpc.product.service.ProductServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class to start the product service.
 *
 * @author CoderSleek
 * @version 1.0
 */
public class Main {
  private static final int SERVER_PORT = 8080;
  private static final Logger log = LoggerFactory.getLogger(Main.class);
  /**
   * Main method to start the product service.
   * instantiates product mock data and starts the gRPC server.
   * constructor injects ProductService into ProductServiceGrpcImpl
   * listens on default server port 8080
   *
   * @param args the input arguments
   * @throws IOException          the io exception
   * @throws InterruptedException the interrupted exception
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    List<Product> products = ProductMocks.getMockProducts();
    var productServiceImpl = new ProductServiceImpl(products);
    var productServiceGrpcImpl = new ProductServiceGrpcImpl(productServiceImpl);

    Server server = ServerBuilder
        .forPort(SERVER_PORT)
        .addService(productServiceGrpcImpl)
        .build();

    log.info("Starting server on port: " + SERVER_PORT);
    log.info("Waiting for request---------");
    server.start();
    server.awaitTermination();
  }
}