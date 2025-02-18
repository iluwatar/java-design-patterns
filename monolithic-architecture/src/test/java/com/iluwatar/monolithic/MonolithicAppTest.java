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
package com.iluwatar.monolithic;

import com.iluwatar.monolithic.controller.OrderController;
import com.iluwatar.monolithic.controller.ProductController;
import com.iluwatar.monolithic.controller.UserController;
import com.iluwatar.monolithic.exceptions.InsufficientStockException;
import com.iluwatar.monolithic.exceptions.NonExistentProductException;
import com.iluwatar.monolithic.exceptions.NonExistentUserException;
import com.iluwatar.monolithic.model.Order;
import com.iluwatar.monolithic.model.Product;
import com.iluwatar.monolithic.model.User;
import com.iluwatar.monolithic.repository.OrderRepository;
import com.iluwatar.monolithic.repository.ProductRepository;
import com.iluwatar.monolithic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

class MonolithicAppTest {

  @Mock
  private UserController userService;

  @Mock
  private ProductController productService;

  @Mock
  private OrderController orderService;

  private EcommerceApp ecommerceApp;

  private ByteArrayOutputStream outputStream;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ecommerceApp = new EcommerceApp(userService, productService, orderService);
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));
  }

  @Test
  void testRegisterUser() {
    String simulatedInput = "John Doe\njohn@example.com\npassword123\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    ecommerceApp.registerUser(new Scanner(System.in, StandardCharsets.UTF_8));

    verify(userService, times(1)).registerUser(any(User.class));
    assertTrue(outputStream.toString().contains("User registered successfully!"));
  }

  @Test
  void testPlaceOrderUserNotFound() {
    UserRepository mockUserRepository = mock(UserRepository.class);
    ProductRepository mockProductRepository = mock(ProductRepository.class);
    OrderRepository mockOrderRepo = mock(OrderRepository.class);

    when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

    OrderController orderCon = new OrderController(mockOrderRepo, mockUserRepository, mockProductRepository);

    Exception exception = assertThrows(NonExistentUserException.class, () -> {
        orderCon.placeOrder(1L, 1L, 5);
    });

    assertEquals("User with ID 1 not found", exception.getMessage());
  }

    @Test
  void testPlaceOrderProductNotFound() {
    UserRepository mockUserRepository = mock(UserRepository.class);
    ProductRepository mockProductRepository = mock(ProductRepository.class);
    OrderRepository mockOrderRepository = mock(OrderRepository.class);

    User mockUser = new User(1L, "John Doe", "john@example.com", "password123");
    when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockUser));

    when(mockProductRepository.findById(1L)).thenReturn(Optional.empty());

    OrderController orderCon = new OrderController(mockOrderRepository, mockUserRepository, mockProductRepository);

    Exception exception = assertThrows(NonExistentProductException.class, () -> {
        orderCon.placeOrder(1L, 1L, 5);
    });

    assertEquals("Product with ID 1 not found", exception.getMessage());
  }



  @Test
  void testOrderConstructor(){
    OrderRepository mockOrderRepository = mock(OrderRepository.class);
    UserRepository mockUserRepository = mock(UserRepository.class);
    ProductRepository mockProductRepository = mock(ProductRepository.class);

    OrderController orderCon = new OrderController(mockOrderRepository, mockUserRepository, mockProductRepository);

    assertNotNull(orderCon);
  }

  @Test
  void testAddProduct() {
    String simulatedInput = "Laptop\nGaming Laptop\n1200.50\n10\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    ecommerceApp.addProduct(new Scanner(System.in, StandardCharsets.UTF_8));

    verify(productService, times(1)).addProduct(any(Product.class));
    assertTrue(outputStream.toString().contains("Product added successfully!"));
  }

  @Test
  void testPlaceOrderSuccess() {
    String simulatedInput = "1\n2\n3\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    Order mockOrder = new Order();
    doReturn(mockOrder).when(orderService).placeOrder(anyLong(), anyLong(), anyInt());

    ecommerceApp.placeOrder(new Scanner(System.in, StandardCharsets.UTF_8));

    verify(orderService, times(1)).placeOrder(anyLong(), anyLong(), anyInt());
    assertTrue(outputStream.toString().contains("Order placed successfully!"));
  }

  @Test
  void testPlaceOrderFailure() {
    String simulatedInput = "1\n2\n3\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    doThrow(new RuntimeException("Product out of stock"))
        .when(orderService).placeOrder(anyLong(), anyLong(), anyInt());

    ecommerceApp.placeOrder(new Scanner(System.in, StandardCharsets.UTF_8));

    verify(orderService, times(1)).placeOrder(anyLong(), anyLong(), anyInt());
    assertTrue(outputStream.toString().contains("Error placing order: Product out of stock"));
  }
  @Test
  void testPlaceOrderInsufficientStock() {
    UserRepository mockUserRepository = mock(UserRepository.class);
    ProductRepository mockProductRepository = mock(ProductRepository.class);
    OrderRepository mockOrderRepository = mock(OrderRepository.class);

    User mockUser = new User(1L, "John Doe", "john@example.com", "password123");
    when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockUser));
    Product mockProduct = new Product(1L, "Laptop", "High-end gaming laptop", 1500.00, 2); // Only 2 in stock
    when(mockProductRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

    OrderController orderCon = new OrderController(mockOrderRepository, mockUserRepository, mockProductRepository);

    Exception exception = assertThrows(InsufficientStockException.class, () -> {
        orderCon.placeOrder(1L, 1L, 5);
    });
    assertEquals("Not enough stock for product 1", exception.getMessage());
}
  @Test
  void testProductConAddProduct() {
    ProductRepository mockProductRepository = mock(ProductRepository.class);

    Product mockProduct = new Product(1L, "Smartphone", "High-end smartphone", 1000.00, 20);

    when(mockProductRepository.save(any(Product.class))).thenReturn(mockProduct);

    ProductController productController = new ProductController(mockProductRepository);

    Product savedProduct = productController.addProduct(mockProduct);

    verify(mockProductRepository, times(1)).save(any(Product.class));

    assertNotNull(savedProduct);
    assertEquals("Smartphone", savedProduct.getName());
    assertEquals("High-end smartphone", savedProduct.getDescription());
    assertEquals(1000.00, savedProduct.getPrice());
    assertEquals(20, savedProduct.getStock());
  }

  @Test
  void testRun() {
    String simulatedInput = """
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
        """;                                          // Exit
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    ByteArrayOutputStream outputTest = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputTest, true, StandardCharsets.UTF_8));

    when(userService.registerUser(any(User.class))).thenReturn(new User(1L, "John Doe", "john@example.com", "password123"));
    when(productService.addProduct(any(Product.class))).thenReturn(new Product(1L, "Laptop", "Gaming Laptop", 1200.50, 10));
    when(orderService.placeOrder(anyLong(), anyLong(), anyInt())).thenReturn(new Order(1L, new User(1L, "John Doe", "john@example.com","password123" ), new Product(1L, "Laptop", "Gaming Laptop", 1200.50, 10), 5, 6002.50));

    ecommerceApp.run();

    verify(userService, times(1)).registerUser(any(User.class));
    verify(productService, times(1)).addProduct(any(Product.class));
    verify(orderService, times(1)).placeOrder(anyLong(), anyLong(), anyInt());

    String output = outputTest.toString(StandardCharsets.UTF_8);
    assertTrue(output.contains("Welcome to the Monolithic E-commerce CLI!"));
    assertTrue(output.contains("Choose an option:"));
    assertTrue(output.contains("Register User"));
    assertTrue(output.contains("Add Product"));
    assertTrue(output.contains("Place Order"));
    assertTrue(output.contains("Exiting the application. Goodbye!"));
}




}