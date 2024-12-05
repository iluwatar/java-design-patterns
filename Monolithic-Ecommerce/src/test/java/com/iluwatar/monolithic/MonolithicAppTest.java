package com.iluwatar.monolithic;

import com.iluwatar.monolithic.controller.OrderCon;
import com.iluwatar.monolithic.controller.ProductCon;
import com.iluwatar.monolithic.controller.UserCon;
import com.iluwatar.monolithic.model.Products;
import com.iluwatar.monolithic.model.Orders;
import com.iluwatar.monolithic.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

class MonolithicAppTest {

  @Mock
  private UserCon userService;

  @Mock
  private ProductCon productService;

  @Mock
  private OrderCon orderService;

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
    // Simulate user input for registering a user
    String simulatedInput = "John Doe\njohn@example.com\npassword123\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    ecommerceApp.registerUser(new Scanner(System.in, StandardCharsets.UTF_8));

    // Verify userService interaction
    verify(userService, times(1)).registerUser(any(User.class));
    assertTrue(outputStream.toString().contains("User registered successfully!"));
  }

  @Test
  void testAddProduct() {
    // Simulate user input for adding a product
    String simulatedInput = "Laptop\nGaming Laptop\n1200.50\n10\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    ecommerceApp.addProduct(new Scanner(System.in, StandardCharsets.UTF_8));

    // Verify productService interaction
    verify(productService, times(1)).addProduct(any(Products.class));
    assertTrue(outputStream.toString().contains("Product added successfully!"));
  }

  @Test
  void testPlaceOrderSuccess() {
    // Simulate user input for placing an order
    String simulatedInput = "1\n2\n3\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    // Mocking placeOrder to return an order object
    Orders mockOrder = new Orders(); // Create a mock or dummy Orders object
    doReturn(mockOrder).when(orderService).placeOrder(anyLong(), anyLong(), anyInt());

    ecommerceApp.placeOrder(new Scanner(System.in, StandardCharsets.UTF_8));

    // Verify orderService interaction
    verify(orderService, times(1)).placeOrder(anyLong(), anyLong(), anyInt());
    assertTrue(outputStream.toString().contains("Order placed successfully!"));
  }

  @Test
  void testPlaceOrderFailure() {
    // Simulate user input for placing an order
    String simulatedInput = "1\n2\n3\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

    doThrow(new RuntimeException("Product out of stock"))
        .when(orderService).placeOrder(anyLong(), anyLong(), anyInt());

    ecommerceApp.placeOrder(new Scanner(System.in, StandardCharsets.UTF_8));

    // Verify orderService interaction
    verify(orderService, times(1)).placeOrder(anyLong(), anyLong(), anyInt());
    assertTrue(outputStream.toString().contains("Error placing order: Product out of stock"));
  }
}