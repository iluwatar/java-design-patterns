package com.iluwatar.transactionaloutbox;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Tests for {@link CustomerService}. */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {

  @Mock private EntityManager entityManager;

  @Mock private EntityTransaction transaction;

  private CustomerService customerService;

  @BeforeEach
  void setup() {
    when(entityManager.getTransaction()).thenReturn(transaction);
    customerService = new CustomerService(entityManager);
  }

  @Test
  void shouldCreateCustomerAndOutboxEventInSameTransaction() throws Exception {
    var username = "testUser";

    customerService.createCustomer(username);

    verify(transaction).begin();
    verify(entityManager, times(2)).persist(any());
    verify(transaction).commit();
    verify(transaction, never()).rollback();
  }

  @Test
  void shouldRollbackTransactionOnFailure() {

    var username = "testUser";
    doThrow(new RuntimeException("Test failure")).when(entityManager).persist(any(Customer.class));

    assertThrows(Exception.class, () -> customerService.createCustomer(username));
    verify(transaction).begin();
    verify(transaction).rollback();
    verify(transaction, never()).commit();
  }
}
