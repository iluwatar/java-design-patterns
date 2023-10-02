package com.iluwatar.verticalslicearchitecture.order;

import com.iluwatar.verticalslicearchitecture.customer.Customer;
import com.iluwatar.verticalslicearchitecture.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateOrder() {
    Customer customer = Customer.builder().id(1).name("John Doe").email("john.doe@example.com").build();
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();
    Orders order = new Orders(1, customer, product);

    when(orderRepository.save(any(Orders.class))).thenReturn(order);

    orderService.createOrder(1, customer, product);

    Mockito.verify(orderRepository).save(any(Orders.class));
  }

  @Test
  void testGetOrderById() {
    Customer customer = Customer.builder().id(1).name("John Doe").email("john.doe@example.com").build();
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();
    Orders order = new Orders(1, customer, product);

    when(orderRepository.findById(1)).thenReturn(Optional.of(order));

    Orders result = orderService.getOrderById(1);

    assertEquals(order, result);
  }

  @Test
  void testGetOrdersByCustomer() {
    Customer customer = Customer.builder().id(1).name("John Doe").email("john.doe@example.com").build();
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();
    Orders order = new Orders(1, customer, product);

    List<Orders> ordersList = new ArrayList<>();
    ordersList.add(order);

    when(orderRepository.findByCustomer(customer)).thenReturn(ordersList);

    List<Orders> result = orderService.getOrdersByCustomer(customer);

    assertEquals(ordersList, result);
  }
}
