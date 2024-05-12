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
package com.iluwatar.datatransfer;

import com.iluwatar.datatransfer.customer.CustomerDto;
import com.iluwatar.datatransfer.customer.CustomerResource;
import com.iluwatar.datatransfer.product.Product;
import com.iluwatar.datatransfer.product.ProductDto;
import com.iluwatar.datatransfer.product.ProductResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The Data Transfer Object pattern is a design pattern in which an data transfer object is used to
 * serve related information together to avoid multiple call for each piece of information.
 *
 * <p>In the first example, {@link App} is a customer details consumer i.e. client to
 * request for customer details to server. {@link CustomerResource} act as server to serve customer
 * information. {@link CustomerDto} is data transfer object to share customer information.
 *
 * <p>In the second example, {@link App} is a product details consumer i.e. client to
 * request for product details to server. {@link ProductResource} acts as server to serve
 * product information. {@link ProductDto} is data transfer object to share product information.
 *
 * <p>The pattern implementation is a bit different in each of the examples. The first can be
 * thought as a traditional example and the second is an enum based implementation.
 *
 */
@Slf4j
public class App {

  /**
   * Method as act client and request to server for details.
   *
   * @param args program argument.
   */
  public static void main(String[] args) {

    // Example 1: Customer DTO
    var customerOne = new CustomerDto("1", "Kelly", "Brown");
    var customerTwo = new CustomerDto("2", "Alfonso", "Bass");
    var customers = new ArrayList<>(List.of(customerOne, customerTwo));

    var customerResource = new CustomerResource(customers);

    LOGGER.info("All customers:");
    var allCustomers = customerResource.customers();
    printCustomerDetails(allCustomers);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Deleting customer with id {1}");
    customerResource.delete(customerOne.id());
    allCustomers = customerResource.customers();
    printCustomerDetails(allCustomers);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Adding customer three}");
    var customerThree = new CustomerDto("3", "Lynda", "Blair");
    customerResource.save(customerThree);
    allCustomers = customerResource.customers();
    printCustomerDetails(allCustomers);

    // Example 2: Product DTO

    Product tv = Product.builder().id(1L).name("TV").supplier("Sony").price(1000D).cost(1090D).build();
    Product microwave =
        Product.builder()
            .id(2L)
            .name("microwave")
            .supplier("Delonghi")
            .price(1000D)
            .cost(1090D).build();
    Product refrigerator =
        Product.builder()
            .id(3L)
            .name("refrigerator")
            .supplier("Botsch")
            .price(1000D)
            .cost(1090D).build();
    Product airConditioner =
        Product.builder()
            .id(4L)
            .name("airConditioner")
            .supplier("LG")
            .price(1000D)
            .cost(1090D).build();
    List<Product> products =
        new ArrayList<>(Arrays.asList(tv, microwave, refrigerator, airConditioner));
    ProductResource productResource = new ProductResource(products);

    LOGGER.info(
        "####### List of products including sensitive data just for admins: \n {}",
        Arrays.toString(productResource.getAllProductsForAdmin().toArray()));
    LOGGER.info(
        "####### List of products for customers: \n {}",
        Arrays.toString(productResource.getAllProductsForCustomer().toArray()));

    LOGGER.info("####### Going to save Sony PS5 ...");
    ProductDto.Request.Create createProductRequestDto =
        new ProductDto.Request.Create()
            .setName("PS5")
            .setCost(1000D)
            .setPrice(1220D)
            .setSupplier("Sony");
    productResource.save(createProductRequestDto);
    LOGGER.info(
        "####### List of products after adding PS5: {}",
        Arrays.toString(productResource.products().toArray()));
  }

  private static void printCustomerDetails(List<CustomerDto> allCustomers) {
    allCustomers.forEach(customer -> LOGGER.info(customer.firstName()));
  }
}
