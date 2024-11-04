package com.iluwatar.idempotentconsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestStateMachineTests {
  private RequestStateMachine requestStateMachine;

  @BeforeEach
  public void setUp() {
    requestStateMachine = new RequestStateMachine();
  }

  @Test
  void transitionPendingToStarted() {
    Request startedRequest = requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.PENDING),
        Request.Status.STARTED);
    assertEquals(Request.Status.STARTED, startedRequest.getStatus());
  }

  @Test
  void transitionAnyToPending_shouldThrowError() {
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.PENDING),
            Request.Status.PENDING));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.STARTED),
            Request.Status.PENDING));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.PENDING));
  }

  @Test
  void transitionCompletedToAny_shouldThrowError() {
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.PENDING));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.STARTED));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.COMPLETED));
  }

  @Test
  void transitionStartedToCompleted() {
    Request completedRequest = requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.STARTED),
        Request.Status.COMPLETED);
    assertEquals(Request.Status.COMPLETED, completedRequest.getStatus());
  }


}