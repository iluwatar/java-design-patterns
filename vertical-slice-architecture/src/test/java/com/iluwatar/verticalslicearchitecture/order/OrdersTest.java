package com.iluwatar.verticalslicearchitecture.order;

import com.iluwatar.verticalslicearchitecture.customer.Customer;
import com.iluwatar.verticalslicearchitecture.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrdersTest {

  @BeforeEach
  void setUp() {
  }

  @Test
  void testOrdersEntity() {
    Customer customer = Customer.builder().id(1).name("John Doe").email("john.doe@example.com").build();
    Product product = Product.builder().id(1).name("Sample Product").price(100.0).build();

    Orders orders = new Orders(1, customer, product);

    assertEquals(1, orders.getId());
    assertEquals(customer, orders.getCustomer());
    assertEquals(product, orders.getProduct());
  }
}
