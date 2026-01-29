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
package com.iluwatar.datatransfer

// ABOUTME: Entry point demonstrating the Data Transfer Object design pattern.
// ABOUTME: Shows two DTO examples: a simple customer DTO and an enum-based product DTO hierarchy.

import com.iluwatar.datatransfer.customer.CustomerDto
import com.iluwatar.datatransfer.customer.CustomerResource
import com.iluwatar.datatransfer.product.Product
import com.iluwatar.datatransfer.product.ProductDto
import com.iluwatar.datatransfer.product.ProductResource
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Data Transfer Object pattern is a design pattern in which a data transfer object is used to
 * serve related information together to avoid multiple calls for each piece of information.
 *
 * In the first example, [main] is a customer details consumer, i.e. the client requesting
 * customer details from the server. [CustomerResource] acts as server to serve customer information.
 * [CustomerDto] is the data transfer object to share customer information.
 *
 * In the second example, [main] is a product details consumer, i.e. the client requesting
 * product details from the server. [ProductResource] acts as server to serve product information.
 * [ProductDto] is the data transfer object to share product information.
 *
 * The pattern implementation is a bit different in each of the examples. The first can be
 * thought of as a traditional example and the second is a namespace-based implementation.
 */
fun main() {
    // Example 1: Customer DTO
    val customerOne = CustomerDto("1", "Kelly", "Brown")
    val customerTwo = CustomerDto("2", "Alfonso", "Bass")
    val customers = mutableListOf(customerOne, customerTwo)

    val customerResource = CustomerResource(customers)

    logger.info { "All customers:" }
    var allCustomers = customerResource.customers
    printCustomerDetails(allCustomers)

    logger.info { "----------------------------------------------------------" }

    logger.info { "Deleting customer with id {1}" }
    customerResource.delete(customerOne.id)
    allCustomers = customerResource.customers
    printCustomerDetails(allCustomers)

    logger.info { "----------------------------------------------------------" }

    logger.info { "Adding customer three}" }
    val customerThree = CustomerDto("3", "Lynda", "Blair")
    customerResource.save(customerThree)
    allCustomers = customerResource.customers
    printCustomerDetails(allCustomers)

    // Example 2: Product DTO
    val tv = Product(id = 1L, name = "TV", supplier = "Sony", price = 1000.0, cost = 1090.0)
    val microwave = Product(id = 2L, name = "microwave", supplier = "Delonghi", price = 1000.0, cost = 1090.0)
    val refrigerator = Product(id = 3L, name = "refrigerator", supplier = "Botsch", price = 1000.0, cost = 1090.0)
    val airConditioner = Product(id = 4L, name = "airConditioner", supplier = "LG", price = 1000.0, cost = 1090.0)
    val products = mutableListOf(tv, microwave, refrigerator, airConditioner)
    val productResource = ProductResource(products)

    logger.info {
        "####### List of products including sensitive data just for admins: \n ${
            productResource.getAllProductsForAdmin().toTypedArray().contentToString()
        }"
    }
    logger.info {
        "####### List of products for customers: \n ${
            productResource.getAllProductsForCustomer().toTypedArray().contentToString()
        }"
    }

    logger.info { "####### Going to save Sony PS5 ..." }
    val createProductRequestDto =
        ProductDto.Request.Create(
            name = "PS5",
            cost = 1000.0,
            price = 1220.0,
            supplier = "Sony",
        )
    productResource.save(createProductRequestDto)
    logger.info {
        "####### List of products after adding PS5: ${
            productResource.products.toTypedArray().contentToString()
        }"
    }
}

private fun printCustomerDetails(allCustomers: List<CustomerDto>) {
    allCustomers.forEach { customer -> logger.info { customer.firstName } }
}
