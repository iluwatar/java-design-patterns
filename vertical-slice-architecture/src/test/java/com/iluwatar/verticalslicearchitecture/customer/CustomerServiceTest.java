package com.iluwatar.verticalslicearchitecture.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class CustomerServiceTest {
  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void testCreateCustomer() {

    Customer newCustomer = Customer.builder().build();

    when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);
    customerService.createCustomer(newCustomer);
    verify(customerRepository, times(1)).save(newCustomer);
  }

  @Test
  void testGetCustomerById() {
    int customerId = 1;
    Customer existingCustomer = new Customer(customerId, "John Doe", "john.doe@example.com");

    /*
     * Mocking the behavior of customerRepository.findById
     */
    when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

    Customer retrievedCustomer = customerService.getCustomerById(customerId);

    assertNotNull(retrievedCustomer);
    assertEquals(existingCustomer, retrievedCustomer);
  }

  @Test
  void testGetAllCustomers() {
    List<Customer> customers = new ArrayList<>();
    customers.add(new Customer(1, "John Doe", "john.doe@example.com"));
    customers.add(new Customer(2, "Jane Smith", "jane.smith@example.com"));

    /*
     * Mocking the behavior of customerRepository.findAll
     */
    when(customerRepository.findAll()).thenReturn(customers);

    List<Customer> retrievedCustomers = customerService.getAllCustomers();

    assertNotNull(retrievedCustomers);
    assertEquals(customers.size(), retrievedCustomers.size());
    assertEquals(customers, retrievedCustomers);
  }
}