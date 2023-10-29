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
 * OrderModuleTest tests the functionalities of the OrderModule
 * by mocking file operations to simulate database interactions.
 */
public class OrderModuleTest {

  /**
   * Mocked content of the database.
   */
  private static final String MOCK_DB_CONTENT =
      """
          CUSTOMERS
          ID, CREDIT_LIMIT
          234, 60000.0
          235, 1000.0

          ORDERS
          ID, CUSTOMER_ID, STATUS, TOTAL
          4567, 234, ACCEPTED, 54044.30
          """;
  private OrderModule module;

  /**
   * Initializes the OrderModule for each test.
   */
  @BeforeEach
  void setUp() {
    module = new OrderModule();
  }

  /**
   * Tests fetching the total order details by customer ID.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testGetOrderTotalByCustomerId() throws Exception {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenReturn(new ArrayList<>(List.of(MOCK_DB_CONTENT.split("\n"))));

      String[] result = module.getOrderTotalByCustomerId(234);
      assertArrayEquals(new String[]{"4567", "234", "ACCEPTED", "54044.30"}, result);
    }
  }

  /**
   * Tests making a new order for a customer.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testMakeOrder() throws Exception {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenReturn(new ArrayList<>(List.of(MOCK_DB_CONTENT.split("\n"))));

      String newOrderId = module.makeOrder(234, 5000.0);
      assertEquals("4568", newOrderId);

      mockedFiles.verify(() -> java.nio.file.Files.write(any(Path.class), anyList()), times(1));
    }
  }

  /**
   * Tests making an order where the credit limit is exceeded.
   */
  @Test
  void testMakeOrder_CreditLimitExceeded() {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenReturn(new ArrayList<>(List.of(MOCK_DB_CONTENT.split("\n"))));

      Exception exception = assertThrows(Exception.class, () -> module.makeOrder(234, 70000.0));

      String expectedMessage = "Exceed the CREDIT_LIMIT.";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage));

      // Verify that no new order was added to the DB.
      mockedFiles.verify(() -> java.nio.file.Files.write(any(Path.class), anyList()), times(0));
    }
  }
}
