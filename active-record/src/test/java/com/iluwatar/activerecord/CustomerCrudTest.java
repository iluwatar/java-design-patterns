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

    customer.save();

    Customer customerTwo = new Customer();
    customerTwo.setId(2L);
    customerTwo.setCustomerNumber("C798237");
    customerTwo.setFirstName("SecondCustomerName");
    customerTwo.setLastName("SecondCustomerLastName");

    customerTwo.save();

    // find all the customers
    List<Customer> customers = customerTwo.findAll();
    Customer firstCustomer = customers.get(0);
    assertEquals(2, customers.size());
    assertEquals(1L, firstCustomer.getId());
    assertEquals("C123", firstCustomer.getCustomerNumber());
    assertEquals("John", firstCustomer.getFirstName());
    assertEquals("Smith", firstCustomer.getLastName());

    // find the second customer
    Customer secondCustomer =
        new Customer().findById(2L); // TODO: has to be referenced fom the static context
    assertEquals(2L, secondCustomer.getId());
    assertEquals("C798237", secondCustomer.getCustomerNumber());
    assertEquals("SecondCustomerName", secondCustomer.getFirstName());
    assertEquals("SecondCustomerLastName", secondCustomer.getLastName());
  }
}
