package com.iluwatar.verticalslicearchitecture.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class CustomerViewTest {

  @Mock
  private CustomerService customerService;

  private CustomerView customerView;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    customerView = new CustomerView(customerService);
  }

  @Test
  void testRender() {
    when(customerService.getAllCustomers()).thenReturn(List.of(
            Customer.builder().id(1).name("John Doe").email("john.doe@example.com").build(),
            Customer.builder().id(2).name("Jone Doe").email("jone.doe@example.com").build()
    ));

    customerView.render();

    verify(customerService, times(1)).getAllCustomers();
  }
}