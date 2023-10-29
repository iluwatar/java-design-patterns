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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link OrderController} class.
 *
 * <p>This class tests the endpoints provided by the OrderController and
 * ensures that they behave as expected when given various inputs.
 */
class OrderControllerTest {

  // MockMvc instance to mock and test Spring MVC behavior
  private MockMvc mockMvc;

  // Mock instance of the OrderServiceInterface interface
  @Mock
  private OrderServiceInterface orderService;

  // Controller instance with mocked services injected
  @InjectMocks
  private OrderController orderController;

  /**
   * Setup method to initialize mocks before each test execution.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
  }

  /**
   * Test case for fetching the total orders of a customer.
   *
   * <p>This test ensures that the endpoint to retrieve the total orders for a customer
   * by their ID works as expected.
   *
   * @throws Exception If any exception occurs during the test execution.
   */
  @Test
  void testFindOrderTotalByCustomerId() throws Exception {
    when(orderService.getOrderTotalByCustomerId(234)).thenReturn(Optional.of(new String[]{"54044.30"}));

    mockMvc.perform(get("/api/orders/getTotal/234"))
        .andExpect(status().isOk())
        .andExpect(content().string("[\"54044.30\"]"));

    verify(orderService, times(1)).getOrderTotalByCustomerId(234);
  }

  /**
   * Test case for creating an order for a customer.
   *
   * <p>This test ensures that the endpoint to create an order for a customer
   * behaves as expected.
   *
   * @throws Exception If any exception occurs during the test execution.
   */
  @Test
  void testCreateOrder() throws Exception {
    when(orderService.makeOrder(235, 300.0)).thenReturn("4568");

    mockMvc.perform(post("/api/orders/create/235")
            .param("amount", "300"))
        .andExpect(status().isOk())
        .andExpect(content().string("Order created successfully with ID: 4568"));

    verify(orderService, times(1)).makeOrder(235, 300.0);
  }
}
