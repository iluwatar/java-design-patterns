package com.iluwater.monolithic.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

/**
 * CombinedTest tests the functionalities of CustomerModule and OrderModule
 * by mocking file operations to simulate database interactions.
 */
public class CombinedTest {

  private CustomerModule customerModule;
  private OrderModule orderModule;
  private List<String> mockDbContent;

  /**
   * Initializes test data and setups required modules.
   */
  @BeforeEach
  void setUp() {
    customerModule = new CustomerModule();
    orderModule = new OrderModule();

    // Initialize with default content
    mockDbContent = new ArrayList<>(List.of(
        "CUSTOMERS",
        "ID, CREDIT_LIMIT",
        "234, 60000.0",
        "235, 1000.0",
        "",
        "ORDERS",
        "ID, CUSTOMER_ID, STATUS, TOTAL",
        "4567, 234, ACCEPTED, 54044.30"
    ));
  }

  /**
   * Tests the scenario of adding a new customer and placing an order.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testNewCustomerAndMakeOrder() throws Exception {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {

      // Mock the read operation to return our in-memory content
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenAnswer(invocation -> new ArrayList<>(mockDbContent));

      // Mock the write operation to update our in-memory content
      mockedFiles.when(() -> java.nio.file.Files.write(any(Path.class), anyList()))
          .thenAnswer(invocation -> {
            mockDbContent.clear();
            mockDbContent.addAll(invocation.getArgument(1));
            return null;
          });

      String newCustomerId = customerModule.newCustomer(5000.0);
      assertEquals("236", newCustomerId);

      String newOrderId = orderModule.makeOrder(Integer.parseInt(newCustomerId), 4500.0);
      assertEquals("4568", newOrderId);

      // Check that our in-memory "file" has been updated
      assertTrue(mockDbContent.contains("236, 5000.0"));
      assertTrue(mockDbContent.contains("4568, 236, ACCEPTED, 4500.0"));

      // Verify file writes
      mockedFiles.verify(() -> java.nio.file.Files.write(any(Path.class), anyList()), times(2));
    }
  }

  /**
   * Tests placing an order using a non-existent customer ID.
   */
  @Test
  void testMakeOrderWithNonExistentCustomer() {
    try (MockedStatic<java.nio.file.Files> ignored = mockFiles()) {
      Exception exception = assertThrows(Exception.class, () -> {
        orderModule.makeOrder(999, 5000.0); // 999 is a non-existent customer ID
      });

      String expectedMessage = "Error getting data of 999 : customerID not found";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage));
    }
  }

  /**
   * Tests the scenario where a customer places multiple orders
   * and exceeds their credit limit.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testExceedCreditLimitWithMultipleOrders() throws Exception {
    try (MockedStatic<java.nio.file.Files> ignored = mockFiles()) {
      orderModule.makeOrder(234, 4000.0);
      Exception exception = assertThrows(Exception.class, () -> orderModule.makeOrder(234, 2000.0));

      String expectedMessage = "Exceed the CREDIT_LIMIT.";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage));
    }
  }

  /**
   * Tests querying for a non-existent customer.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testQueryForNonExistentCustomer() throws Exception {
    try (MockedStatic<java.nio.file.Files> ignored = mockFiles()) {
      String actualMessage = customerModule.getCustomerById(999)[0];
      String expectedMessage = "Error getting data of 999 : customerID not found";
      assertTrue(actualMessage.contains(expectedMessage));
    }
  }

  /**
   * Tests updating the credit limit for a non-existent customer.
   */
  @Test
  void testUpdateCreditLimitForNonExistentCustomer() {
    try (MockedStatic<java.nio.file.Files> ignored = mockFiles()) {
      Exception exception = assertThrows(Exception.class, () -> customerModule.updateCreditLimit(999, 60000.0));

      String expectedMessage = "Customer not found.";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage));
    }
  }

  /**
   * Tests getting the total order value for a customer who hasn't placed any orders.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testGetOrderTotalForCustomerWithNoOrders() throws Exception {
    try (MockedStatic<java.nio.file.Files> ignored = mockFiles()) {
      String actualMessage = orderModule.getOrderTotalByCustomerId(235)[0];
      String expectedMessage = "No order found for customer 235.";
      assertTrue(actualMessage.contains(expectedMessage));
    }
  }

  /**
   * Mocks file operations for the tests.
   *
   * @return MockedStatic instance for java.nio.file.Files.
   */
  private MockedStatic<java.nio.file.Files> mockFiles() {
    MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class);

    // Mock the read operation
    mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
        .thenAnswer(invocation -> new ArrayList<>(mockDbContent));

    // Mock the write operation
    mockedFiles.when(() -> java.nio.file.Files.write(any(Path.class), anyList()))
        .thenAnswer(invocation -> {
          mockDbContent.clear();
          mockDbContent.addAll(invocation.getArgument(1));
          return null;
        });

    return mockedFiles;
  }
}
