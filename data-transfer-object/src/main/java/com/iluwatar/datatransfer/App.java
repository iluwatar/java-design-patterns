/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

    LOGGER.info("All customers:-");
    var allCustomers = customerResource.getAllCustomers();
    printCustomerDetails(allCustomers);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Deleting customer with id {1}");
    customerResource.delete(customerOne.getId());
    allCustomers = customerResource.getAllCustomers();
    printCustomerDetails(allCustomers);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Adding customer three}");
    var customerThree = new CustomerDto("3", "Lynda", "Blair");
    customerResource.save(customerThree);
    allCustomers = customerResource.getAllCustomers();
    printCustomerDetails(allCustomers);

    // Example 2: Product DTO
    Product tv =
        new Product().setId(1L).setName("TV").setSupplier("Sony").setPrice(1000D).setCost(1090D);
    Product microwave =
        new Product()
            .setId(2L)
            .setName("microwave")
            .setSupplier("Delonghi")
            .setPrice(1000D)
            .setCost(1090D);
    Product refrigerator =
        new Product()
            .setId(3L)
            .setName("refrigerator")
            .setSupplier("Botsch")
            .setPrice(1000D)
            .setCost(1090D);
    Product airConditioner =
        new Product()
            .setId(4L)
            .setName("airConditioner")
            .setSupplier("LG")
            .setPrice(1000D)
            .setCost(1090D);
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
        Arrays.toString(productResource.getProducts().toArray()));
  }

  private static void printCustomerDetails(List<CustomerDto> allCustomers) {
    allCustomers.forEach(customer -> LOGGER.info(customer.getFirstName()));
  }
}
