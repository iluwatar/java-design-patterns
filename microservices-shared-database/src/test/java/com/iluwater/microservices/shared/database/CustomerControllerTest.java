package com.iluwater.microservices.shared.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link CustomerController} class.
 *
 * <p>This class tests the endpoints provided by the CustomerController and
 * ensures that they behave as expected when given various inputs.
 */
class CustomerControllerTest {

  // MockMvc instance to mock and test Spring MVC behavior
  private MockMvc mockMvc;

  // Mock instance of the CustomerServiceInterface interface
  @Mock
  private CustomerServiceInterface customerService;

  // Controller instance with mocked services injected
  @InjectMocks
  private CustomerController customerController;

  /**
   * Setup method to initialize mocks before each test execution.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }

  /**
   * Test case for getting a customer's details.
   *
   * <p>This test ensures that the endpoint to retrieve a customer by their ID
   * works as expected.
   *
   * @throws Exception If any exception occurs during the test execution.
   */
  @Test
  void testGetCustomer() throws Exception {
    when(customerService.getCustomerById(235)).thenReturn(Optional.of(new String[]{"235", "1000"}));

    mockMvc.perform(get("/api/customers/get/235"))
        .andExpect(status().isOk())
        .andExpect(content().string("[\"235\",\"1000\"]"));

    verify(customerService, times(1)).getCustomerById(235);
  }

  /**
   * Test case for updating a customer's credit limit.
   *
   * <p>This test ensures that the endpoint to update a customer's credit limit
   * behaves as expected.
   *
   * @throws Exception If any exception occurs during the test execution.
   */
  @Test
  void testUpdateCreditLimit() throws Exception {
    doNothing().when(customerService).updateCreditLimit(235, 2000.0);

    mockMvc.perform(put("/api/customers/creditLimit/235")
            .param("newCreditLimit", "2000"))
        .andExpect(status().isOk())
        .andExpect(content().string("Credit limit updated successfully!"));

    verify(customerService, times(1)).updateCreditLimit(235, 2000.0);
  }

  /**
   * Test case for creating a new customer.
   *
   * <p>This test ensures that the endpoint to create a new customer
   * behaves as expected.
   *
   * @throws Exception If any exception occurs during the test execution.
   */
  @Test
  void testCreateCustomer() throws Exception {
    when(customerService.newCustomer(3000.0)).thenReturn("236");

    mockMvc.perform(post("/api/customers/create/3000"))
        .andExpect(status().isOk())
        .andExpect(content().string("Customer created successfully with ID: 236"));

    verify(customerService, times(1)).newCustomer(3000.0);
  }
}
