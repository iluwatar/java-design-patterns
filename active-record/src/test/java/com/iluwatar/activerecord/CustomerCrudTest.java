package com.iluwatar.activerecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerCrudTest extends BaseTest {

  @Test
  void shouldExecuteCustomerLifecycle() {
    // save two customers
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setCustomerNumber("C123");
    customer.setFirstName("John");
    customer.setLastName("Smith");

    customer.save(Customer.class);

    Customer customerTwo = new Customer();
    customerTwo.setId(2L);
    customerTwo.setCustomerNumber("C798237");
    customerTwo.setFirstName("SecondCustomerName");
    customerTwo.setLastName("SecondCustomerLastName");

    customerTwo.save(Customer.class);

    // find all the customers
    List<Customer> customers = customerTwo.findAll(Customer.class);
    Customer firstCustomer = customers.get(0);
    assertEquals(2, customers.size());
    assertEquals(1L, firstCustomer.getId());
    assertEquals("C123", firstCustomer.getCustomerNumber());
    assertEquals("John", firstCustomer.getFirstName());
    assertEquals("Smith", firstCustomer.getLastName());

    // find the second customer
    Customer secondCustomer = Customer.findById(2L, Customer.class);
    assertEquals(2L, secondCustomer.getId());
    assertEquals("C798237", secondCustomer.getCustomerNumber());
    assertEquals("SecondCustomerName", secondCustomer.getFirstName());
    assertEquals("SecondCustomerLastName", secondCustomer.getLastName());
  }
}
