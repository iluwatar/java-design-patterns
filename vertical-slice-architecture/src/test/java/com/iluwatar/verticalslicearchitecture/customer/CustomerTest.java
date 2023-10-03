package com.iluwatar.verticalslicearchitecture.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

  @Test
  void testCustomerEntity() {
    // Arrange
    Integer id = 1;
    String name = "John Doe";
    String email = "john.doe@example.com";

    // Act
    Customer customer = Customer.builder()
            .id(id)
            .name(name)
            .email(email)
            .build();

    // Assert
    assertEquals(id, customer.getId());
    assertEquals(name, customer.getName());
    assertEquals(email, customer.getEmail());
  }
}