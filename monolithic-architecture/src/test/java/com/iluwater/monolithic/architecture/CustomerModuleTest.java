package com.iluwater.monolithic.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

/**
 * CustomerModuleTest tests the functionalities of the CustomerModule
 * by mocking file operations to simulate database interactions.
 */
public class CustomerModuleTest {

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
  private CustomerModule module;

  /**
   * Initializes the CustomerModule for each test.
   */
  @BeforeEach
  void setUp() {
    module = new CustomerModule();
  }

  /**
   * Tests fetching customer data by their ID.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testGetCustomerById() throws Exception {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenReturn(new ArrayList<>(List.of(MOCK_DB_CONTENT.split("\n"))));

      assertEquals("234", module.getCustomerById(234)[0]);
      assertEquals("60000.0", module.getCustomerById(234)[1]);
    }
  }

  /**
   * Tests updating the credit limit for a customer.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testUpdateCreditLimit() throws Exception {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenReturn(new ArrayList<>(List.of(MOCK_DB_CONTENT.split("\n"))));

      module.updateCreditLimit(234, 65000.0);

      mockedFiles.verify(() -> java.nio.file.Files.write(any(Path.class), anyList()), times(1));
    }
  }

  /**
   * Tests creating a new customer.
   *
   * @throws Exception If there's an error during test execution
   */
  @Test
  void testNewCustomer() throws Exception {
    try (MockedStatic<java.nio.file.Files> mockedFiles = Mockito.mockStatic(java.nio.file.Files.class)) {
      mockedFiles.when(() -> java.nio.file.Files.readAllLines(any(Path.class)))
          .thenReturn(new ArrayList<>(List.of(MOCK_DB_CONTENT.split("\n"))));

      String newCustomerId = module.newCustomer(5000.0);

      assertEquals("236", newCustomerId);

      mockedFiles.verify(() -> java.nio.file.Files.write(any(Path.class), anyList()), times(1));
    }
  }
}
