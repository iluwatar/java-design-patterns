package com.iluwater.microservices.shared.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the combined interactions between {@link CustomerController} and {@link OrderController}.
 *
 * <p>This class tests the behavior of endpoints in both controllers when they have dependent operations.
 * For example, we may want to simulate the scenario where a customer is created and then an order is placed for that customer.
 */
@TestPropertySource(locations = "classpath:test.properties")
public class CombinedControllerTest {

  // MockMvc instance to simulate Spring MVC behavior
  private MockMvc mockMvc;

  // Mock instances of the services
  @Mock
  private CustomerServiceInterface customerService;

  @Mock
  private OrderServiceInterface orderService;

  // Controller instances with injected mock services
  @InjectMocks
  private CustomerController customerController;

  @InjectMocks
  private OrderController orderController;

  /**
   * Setup method to initialize mocks and setup MockMvc with both controllers.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(customerController, orderController).build();
  }

  /**
   * Test case for creating a customer and then creating an order for that customer.
   *
   * <p>This test simulates a scenario where a new customer is first created and then
   * an order is placed for that customer. Both operations are expected to succeed.
   *
   * @throws Exception If any exception occurs during the test execution.
   */
  @Test
  void testCreateCustomerAndOrder() throws Exception {
    // Mock the creation of a customer
    when(customerService.newCustomer(1000.0)).thenReturn("236");
    mockMvc.perform(post("/api/customers/create/1000"))
        .andExpect(status().isOk())
        .andExpect(content().string("Customer created successfully with ID: 236"));

    // Mock the creation of an order for the customer
    when(orderService.makeOrder(236, 300.0)).thenReturn("4568");
    mockMvc.perform(post("/api/orders/create/236")
            .param("amount", "300"))
        .andExpect(status().isOk())
        .andExpect(content().string("Order created successfully with ID: 4568"));

    // Verify service calls
    verify(customerService, times(1)).newCustomer(1000.0);
    verify(orderService, times(1)).makeOrder(236, 300.0);
  }
}
