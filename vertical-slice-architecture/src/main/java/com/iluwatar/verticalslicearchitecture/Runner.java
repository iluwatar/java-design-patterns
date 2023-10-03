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
package com.iluwatar.verticalslicearchitecture;

import com.iluwatar.verticalslicearchitecture.customer.Customer;
import com.iluwatar.verticalslicearchitecture.customer.CustomerService;
import com.iluwatar.verticalslicearchitecture.customer.CustomerView;
import com.iluwatar.verticalslicearchitecture.order.OrderService;
import com.iluwatar.verticalslicearchitecture.order.OrderView;
import com.iluwatar.verticalslicearchitecture.product.Product;
import com.iluwatar.verticalslicearchitecture.product.ProductService;
import com.iluwatar.verticalslicearchitecture.product.ProductView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *  Seeding test data.
 */

@Component
@AllArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {

  CustomerService customerService;
  OrderService orderService;
  ProductService productService;

  @Override
  public void run(String... args) {
    initializeData();
    new OrderView(orderService).render(customerService.getCustomerById(1));
    new CustomerView(customerService).render();
    new ProductView(productService).render();
  }

  /**
   * method for data seeds.
   */

  public void initializeData() {

    var customer = Customer.builder().id(1).name("sugan0tech").email("sugan@gmail.com").build();
    customerService.createCustomer(customer);

    var oreo = Product.builder().id(1).price(2.00).name("Oreo").build();
    var cone = Product.builder().id(3).price(1.15).name("Ice Cream Cone").build();
    var apple = Product.builder().id(4).price(2.00).name("Apple").build();
    var sandwich = Product.builder().id(2).price(6.00).name("Sandwich").build();
    productService.createProduct(oreo);
    productService.createProduct(cone);
    productService.createProduct(apple);
    productService.createProduct(sandwich);

    orderService.createOrder(1, customer, oreo);
    orderService.createOrder(3, customer, apple);
    orderService.createOrder(2, customer, sandwich);
  }
}
