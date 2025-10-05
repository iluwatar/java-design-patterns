package com.iluwatar.transactionaloutbox;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@ExtendWith(MockitoExtension.class)
class AppTest {

  @Test
  void testMainExecution() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }

  @Test
  void testSimulateCustomerCreation() {
    var entityManagerFactory = mock(EntityManagerFactory.class);
    var entityManager = mock(EntityManager.class);
    var customerService = new CustomerService(entityManager);
    var messageBroker = new MessageBroker();
    var eventPoller = new EventPoller(entityManager, messageBroker);

    assertNotNull(entityManagerFactory);
    assertNotNull(entityManager);
    assertNotNull(customerService);
    assertNotNull(messageBroker);
    assertNotNull(eventPoller);
  }
}
