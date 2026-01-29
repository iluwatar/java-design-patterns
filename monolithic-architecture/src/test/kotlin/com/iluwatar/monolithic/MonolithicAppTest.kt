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

// ABOUTME: Unit tests for the Monolithic E-commerce application.
// ABOUTME: Tests user registration, product management, and order placement with MockK.
package com.iluwatar.monolithic

import com.iluwatar.monolithic.controller.OrderController
import com.iluwatar.monolithic.controller.ProductController
import com.iluwatar.monolithic.controller.UserController
import com.iluwatar.monolithic.exceptions.InsufficientStockException
import com.iluwatar.monolithic.exceptions.NonExistentProductException
import com.iluwatar.monolithic.exceptions.NonExistentUserException
import com.iluwatar.monolithic.model.Order
import com.iluwatar.monolithic.model.Product
import com.iluwatar.monolithic.model.User
import com.iluwatar.monolithic.repository.OrderRepository
import com.iluwatar.monolithic.repository.ProductRepository
import com.iluwatar.monolithic.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.Optional
import java.util.Scanner

class MonolithicAppTest {
    private lateinit var userService: UserController
    private lateinit var productService: ProductController
    private lateinit var orderService: OrderController
    private lateinit var ecommerceApp: EcommerceApp
    private lateinit var outputStream: ByteArrayOutputStream

    @BeforeEach
    fun setUp() {
        userService = mockk(relaxed = true)
        productService = mockk(relaxed = true)
        orderService = mockk(relaxed = true)
        ecommerceApp = EcommerceApp(userService, productService, orderService)
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream, true, StandardCharsets.UTF_8))
        Locale.setDefault(Locale.US)
    }

    @Test
    fun testRegisterUser() {
        val simulatedInput = "John Doe\njohn@example.com\npassword123\n"
        System.setIn(ByteArrayInputStream(simulatedInput.toByteArray(StandardCharsets.UTF_8)))

        ecommerceApp.registerUser(Scanner(System.`in`, StandardCharsets.UTF_8))

        verify(exactly = 1) { userService.registerUser(any()) }
        assertTrue(outputStream.toString().contains("User registered successfully!"))
    }

    @Test
    fun testPlaceOrderUserNotFound() {
        val mockUserRepository = mockk<UserRepository>()
        val mockProductRepository = mockk<ProductRepository>()
        val mockOrderRepo = mockk<OrderRepository>()

        every { mockUserRepository.findById(1L) } returns Optional.empty()

        val orderCon = OrderController(mockOrderRepo, mockUserRepository, mockProductRepository)

        val exception =
            assertThrows(NonExistentUserException::class.java) {
                orderCon.placeOrder(1L, 1L, 5)
            }

        assertEquals("User with ID 1 not found", exception.message)
    }

    @Test
    fun testPlaceOrderProductNotFound() {
        val mockUserRepository = mockk<UserRepository>()
        val mockProductRepository = mockk<ProductRepository>()
        val mockOrderRepository = mockk<OrderRepository>()

        val mockUser = User(1L, "John Doe", "john@example.com", "password123")
        every { mockUserRepository.findById(1L) } returns Optional.of(mockUser)
        every { mockProductRepository.findById(1L) } returns Optional.empty()

        val orderCon = OrderController(mockOrderRepository, mockUserRepository, mockProductRepository)

        val exception =
            assertThrows(NonExistentProductException::class.java) {
                orderCon.placeOrder(1L, 1L, 5)
            }

        assertEquals("Product with ID 1 not found", exception.message)
    }

    @Test
    fun testOrderConstructor() {
        val mockOrderRepository = mockk<OrderRepository>()
        val mockUserRepository = mockk<UserRepository>()
        val mockProductRepository = mockk<ProductRepository>()

        val orderCon = OrderController(mockOrderRepository, mockUserRepository, mockProductRepository)

        assertNotNull(orderCon)
    }

    @Test
    fun testAddProduct() {
        val simulatedInput = "Laptop\nGaming Laptop\n1200.50\n10\n"
        System.setIn(ByteArrayInputStream(simulatedInput.toByteArray(StandardCharsets.UTF_8)))

        ecommerceApp.addProduct(Scanner(System.`in`, StandardCharsets.UTF_8))

        verify(exactly = 1) { productService.addProduct(any()) }
        assertTrue(outputStream.toString().contains("Product added successfully!"))
    }

    @Test
    fun testPlaceOrderSuccess() {
        val simulatedInput = "1\n2\n3\n"
        System.setIn(ByteArrayInputStream(simulatedInput.toByteArray(StandardCharsets.UTF_8)))

        val mockOrder = Order()
        every { orderService.placeOrder(any(), any(), any()) } returns mockOrder

        ecommerceApp.placeOrder(Scanner(System.`in`, StandardCharsets.UTF_8))

        verify(exactly = 1) { orderService.placeOrder(any(), any(), any()) }
        assertTrue(outputStream.toString().contains("Order placed successfully!"))
    }

    @Test
    fun testPlaceOrderFailure() {
        val simulatedInput = "1\n2\n3\n"
        System.setIn(ByteArrayInputStream(simulatedInput.toByteArray(StandardCharsets.UTF_8)))

        every { orderService.placeOrder(any(), any(), any()) } throws RuntimeException("Product out of stock")

        ecommerceApp.placeOrder(Scanner(System.`in`, StandardCharsets.UTF_8))

        verify(exactly = 1) { orderService.placeOrder(any(), any(), any()) }
        assertTrue(outputStream.toString().contains("Error placing order: Product out of stock"))
    }

    @Test
    fun testPlaceOrderInsufficientStock() {
        val mockUserRepository = mockk<UserRepository>()
        val mockProductRepository = mockk<ProductRepository>()
        val mockOrderRepository = mockk<OrderRepository>()

        val mockUser = User(1L, "John Doe", "john@example.com", "password123")
        every { mockUserRepository.findById(1L) } returns Optional.of(mockUser)
        val mockProduct = Product(1L, "Laptop", "High-end gaming laptop", 1500.00, 2) // Only 2 in stock
        every { mockProductRepository.findById(1L) } returns Optional.of(mockProduct)

        val orderCon = OrderController(mockOrderRepository, mockUserRepository, mockProductRepository)

        val exception =
            assertThrows(InsufficientStockException::class.java) {
                orderCon.placeOrder(1L, 1L, 5)
            }
        assertEquals("Not enough stock for product 1", exception.message)
    }

    @Test
    fun testProductConAddProduct() {
        val mockProductRepository = mockk<ProductRepository>()

        val mockProduct = Product(1L, "Smartphone", "High-end smartphone", 1000.00, 20)

        every { mockProductRepository.save(any()) } returns mockProduct

        val productController = ProductController(mockProductRepository)

        val savedProduct = productController.addProduct(mockProduct)

        verify(exactly = 1) { mockProductRepository.save(any()) }

        assertNotNull(savedProduct)
        assertEquals("Smartphone", savedProduct.name)
        assertEquals("High-end smartphone", savedProduct.description)
        assertEquals(1000.00, savedProduct.price)
        assertEquals(20, savedProduct.stock)
    }

    @Test
    fun testRun() {
        val simulatedInput =
            """
            1
            John Doe
            john@example.com
            password123
            2
            Laptop
            Gaming Laptop
            1200.50
            10
            3
            1
            1
            2
            4
            """.trimIndent() + "\n" // Exit
        System.setIn(ByteArrayInputStream(simulatedInput.toByteArray(StandardCharsets.UTF_8)))

        val outputTest = ByteArrayOutputStream()
        System.setOut(PrintStream(outputTest, true, StandardCharsets.UTF_8))

        every { userService.registerUser(any()) } returns User(1L, "John Doe", "john@example.com", "password123")
        every { productService.addProduct(any()) } returns Product(1L, "Laptop", "Gaming Laptop", 1200.50, 10)
        every { orderService.placeOrder(any(), any(), any()) } returns
            Order(
                1L,
                User(1L, "John Doe", "john@example.com", "password123"),
                Product(1L, "Laptop", "Gaming Laptop", 1200.50, 10),
                5,
                6002.50,
            )

        ecommerceApp.run()

        verify(exactly = 1) { userService.registerUser(any()) }
        verify(exactly = 1) { productService.addProduct(any()) }
        verify(exactly = 1) { orderService.placeOrder(any(), any(), any()) }

        val output = outputTest.toString(StandardCharsets.UTF_8)
        assertTrue(output.contains("Welcome to the Monolithic E-commerce CLI!"))
        assertTrue(output.contains("Choose an option:"))
        assertTrue(output.contains("Register User"))
        assertTrue(output.contains("Add Product"))
        assertTrue(output.contains("Place Order"))
        assertTrue(output.contains("Exiting the application. Goodbye!"))
    }
}