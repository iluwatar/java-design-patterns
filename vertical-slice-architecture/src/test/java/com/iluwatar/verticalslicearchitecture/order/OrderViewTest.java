package com.iluwatar.verticalslicearchitecture.order;

import com.iluwatar.verticalslicearchitecture.customer.Customer;
import com.iluwatar.verticalslicearchitecture.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderViewTest {

  private OrderService orderService;
  private OrderView orderView;

  @BeforeEach
  void setUp() {
    orderService = Mockito.mock(OrderService.class);
    orderView = new OrderView(orderService);
  }

  @Test
  void testRender() {
    Customer customer = Customer.builder().id(1).name("John Doe").email("john.doe@example.com").build();
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();
    Orders order = new Orders(1, customer, product);

    List<Orders> ordersList = new ArrayList<>();
    ordersList.add(order);

    when(orderService.getOrdersByCustomer(customer)).thenReturn(ordersList);

    orderView.render(customer);

    verify(orderService, times(1)).getOrdersByCustomer(any());
  }
}
