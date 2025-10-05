package com.iluwatar.transactionaloutbox;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Tests for {@link OutboxRepository}. */
@ExtendWith(MockitoExtension.class)
class OutboxRepositoryTests {

  @Mock private EntityManager entityManager;
  private OutboxRepository repository;

  @BeforeEach
  void setup() {
    repository = new OutboxRepository(entityManager);
  }

  @Test
  void shouldSaveAndMarkEventAsProcessed() {
    var event = new OutboxEvent("TEST_EVENT", "payload");

    repository.save(event);
    repository.markAsProcessed(event);

    verify(entityManager).persist(event);
    verify(entityManager).merge(event);
    assertTrue(event.isProcessed());
  }
}
