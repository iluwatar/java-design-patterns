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

// ABOUTME: Main entry point for the Monolithic E-commerce Spring Boot application.
// ABOUTME: Implements a CLI for user management, product management, and order placement.
package com.iluwatar.monolithic

import com.iluwatar.monolithic.controller.OrderController
import com.iluwatar.monolithic.controller.ProductController
import com.iluwatar.monolithic.controller.UserController
import com.iluwatar.monolithic.model.Product
import com.iluwatar.monolithic.model.User
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.nio.charset.StandardCharsets
import java.util.Scanner

private val logger = KotlinLogging.logger {}

/**
 * Main entry point for the Monolithic E-commerce application.
 *
 * Monolithic architecture is a software design pattern where all components of the application
 * (presentation, business logic, and data access layers) are part of a single unified codebase
 * and deployable unit.
 *
 * This example implements a monolithic architecture by integrating user management, product
 * management, and order placement within the same application, sharing common resources and
 * a single database.
 */
@SpringBootApplication
open class EcommerceApp(
    private val userService: UserController,
    private val productService: ProductController,
    private val orderService: OrderController,
) : CommandLineRunner {
    override fun run(vararg args: String) {
        val scanner = Scanner(System.`in`, StandardCharsets.UTF_8)

        logger.info { "Welcome to the Monolithic E-commerce CLI!" }
        while (true) {
            logger.info { "\nChoose an option:" }
            logger.info { "1. Register User" }
            logger.info { "2. Add Product" }
            logger.info { "3. Place Order" }
            logger.info { "4. Exit" }
            logger.info { "Enter your choice: " }

            val userInput = scanner.nextInt()
            scanner.nextLine()

            when (userInput) {
                1 -> registerUser(scanner)
                2 -> addProduct(scanner)
                3 -> placeOrder(scanner)
                4 -> {
                    logger.info { "Exiting the application. Goodbye!" }
                    return
                }
                else -> logger.info { "Invalid choice! Please try again." }
            }
        }
    }

    /** Handles User Registration through user CLI inputs. */
    internal fun registerUser(scanner: Scanner) {
        logger.info { "Enter user details:" }
        logger.info { "Name: " }
        val name = scanner.nextLine()
        logger.info { "Email: " }
        val email = scanner.nextLine()
        logger.info { "Password: " }
        val password = scanner.nextLine()

        val user = User(id = null, name = name, email = email, password = password)
        userService.registerUser(user)
        logger.info { "User registered successfully!" }
    }

    /** Handles the addition of products. */
    internal fun addProduct(scanner: Scanner) {
        logger.info { "Enter product details:" }
        logger.info { "Name: " }
        val name = scanner.nextLine()
        logger.info { "Description: " }
        val description = scanner.nextLine()
        logger.info { "Price: " }
        val price = scanner.nextDouble()
        logger.info { "Stock: " }
        val stock = scanner.nextInt()
        val product = Product(id = null, name = name, description = description, price = price, stock = stock)
        scanner.nextLine()
        productService.addProduct(product)
        logger.info { "Product added successfully!" }
    }

    /** Handles Order Placement through user CLI inputs. */
    internal fun placeOrder(scanner: Scanner) {
        logger.info { "Enter order details:" }
        logger.info { "User ID: " }
        val userId = scanner.nextLong()
        logger.info { "Product ID: " }
        val productId = scanner.nextLong()
        logger.info { "Quantity: " }
        val quantity = scanner.nextInt()
        scanner.nextLine()

        try {
            orderService.placeOrder(userId, productId, quantity)
            logger.info { "Order placed successfully!" }
        } catch (e: Exception) {
            logger.info { "Error placing order: ${e.message}" }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<EcommerceApp>(*args)
}