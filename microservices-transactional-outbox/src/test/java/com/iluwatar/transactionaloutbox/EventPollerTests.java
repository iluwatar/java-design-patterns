package com.iluwatar.transactionaloutbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Tests for {@link EventPoller}. */
@ExtendWith(MockitoExtension.class)
class EventPollerTests {

  private static final String TEST_EVENT = "TEST_EVENT";
  private static final String TEST_PAYLOAD = "payload";

  @Mock private EntityManager entityManager;
  @Mock private EntityTransaction transaction;
  @Mock private MessageBroker messageBroker;
  @Mock private TypedQuery<OutboxEvent> query;
  private EventPoller eventPoller;

  @BeforeEach
  void setup() {
    when(entityManager.getTransaction()).thenReturn(transaction);
    when(entityManager.createQuery(anyString(), eq(OutboxEvent.class))).thenReturn(query);
    when(query.setMaxResults(any(Integer.class))).thenReturn(query);
    when(query.setLockMode(any(LockModeType.class))).thenReturn(query);
    eventPoller = new EventPoller(entityManager, messageBroker);
  }

  @Test
  void shouldProcessEventsSuccessfully() {
    var event = new OutboxEvent("EVENT_1", "payload1");
    when(query.getResultList()).thenReturn(Collections.singletonList(event));

    eventPoller.processOutboxEvents();

    verify(messageBroker).sendMessage(event);
    verify(transaction).begin();
    verify(transaction).commit();
    assertEquals(1, eventPoller.getProcessedEventsCount());
  }

  @Test
  void shouldHandleFailureAndRetry() {
    var event = new OutboxEvent(TEST_EVENT, TEST_PAYLOAD);
    when(query.getResultList()).thenReturn(Collections.singletonList(event));
    doThrow(new RuntimeException("First attempt"))
        .doNothing()
        .when(messageBroker)
        .sendMessage(any(OutboxEvent.class));

    eventPoller.processOutboxEvents();

    verify(messageBroker, times(2)).sendMessage(event);
    assertEquals(1, eventPoller.getProcessedEventsCount());
    assertEquals(0, eventPoller.getFailedEventsCount());
  }

  @Test
  void shouldHandleInterruptedThread() {
    var event = new OutboxEvent(TEST_EVENT, TEST_PAYLOAD);
    when(query.getResultList()).thenReturn(Collections.singletonList(event));
    doThrow(new RuntimeException("Processing fails"))
        .when(messageBroker)
        .sendMessage(any(OutboxEvent.class));
    Thread.currentThread().interrupt();

    eventPoller.processOutboxEvents();

    verify(transaction).begin();
    verify(transaction).commit();
    verify(messageBroker).sendMessage(event);
    assertEquals(0, eventPoller.getProcessedEventsCount());
    assertEquals(
        0, eventPoller.getFailedEventsCount(), "Interrupted events are not counted as failures");
    assertTrue(Thread.interrupted(), "Interrupt flag should be preserved");
  }

  @Test
  void shouldHandleEmptyEventList() {
    when(query.getResultList()).thenReturn(Collections.emptyList());

    eventPoller.processOutboxEvents();

    verify(transaction).begin();
    verify(transaction).commit();
    assertEquals(0, eventPoller.getProcessedEventsCount());
    assertEquals(0, eventPoller.getFailedEventsCount());
  }

  @Test
  void shouldHandleMaxRetryAttempts() {
    var event = new OutboxEvent(TEST_EVENT, TEST_PAYLOAD);
    when(query.getResultList()).thenReturn(Collections.singletonList(event));
    doThrow(new RuntimeException("Failed processing"))
        .when(messageBroker)
        .sendMessage(any(OutboxEvent.class));

    eventPoller.processOutboxEvents();

    verify(messageBroker, times(3)).sendMessage(event);
    assertEquals(0, eventPoller.getProcessedEventsCount());
    assertEquals(1, eventPoller.getFailedEventsCount());
  }

  @Test
  void shouldProcessMultipleEvents() {
    var event1 = new OutboxEvent("EVENT_1", "payload1");
    var event2 = new OutboxEvent("EVENT_2", "payload2");
    when(query.getResultList()).thenReturn(java.util.Arrays.asList(event1, event2));

    eventPoller.processOutboxEvents();

    verify(messageBroker).sendMessage(event1);
    verify(messageBroker).sendMessage(event2);
    verify(transaction).begin();
    verify(transaction).commit();
    assertEquals(2, eventPoller.getProcessedEventsCount());
    assertEquals(0, eventPoller.getFailedEventsCount());
  }

  @Test
  void shouldRollbackTransactionWhenRepositoryFails() {
    var dbException = new RuntimeException("Database connection failed");
    when(query.getResultList()).thenThrow(dbException);

    eventPoller.processOutboxEvents();

    verify(transaction).begin();
    verify(transaction).rollback();
    verify(transaction, never()).commit();

    assertEquals(1, eventPoller.getFailedEventsCount());
    assertEquals(0, eventPoller.getProcessedEventsCount());
  }

  @Test
  void shouldStartAndStopCorrectly() throws Exception {
    eventPoller.start();

    assertFalse(eventPoller.getScheduler().isShutdown());
    assertFalse(eventPoller.getScheduler().isTerminated());

    eventPoller.stop();

    assertTrue(eventPoller.getScheduler().isShutdown());
    assertTrue(eventPoller.getScheduler().awaitTermination(1, TimeUnit.SECONDS));
    assertTrue(eventPoller.getScheduler().isTerminated());
  }
}
