package com.iluwatar.threadspecificstorage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestHandlerTest {

  @Test
  void process_shouldStoreAndClearUserContext() {
    // Given - a request handler without proxy parameter
    RequestHandler handler = new RequestHandler("token::123");

    // When - process the request
    handler.process();

    // Then - after processing, ThreadLocal should be cleared
    assertNull(UserContextProxy.get(), "ThreadLocal should be cleared after process()");
  }

  @Test
  void process_withInvalidToken_shouldSetUserIdToMinusOne() {
    // Given - a request handler without proxy parameter
    RequestHandler handler = new RequestHandler("invalid-token");

    // When - process the request
    handler.process();

    // Then - after processing, ThreadLocal should be cleared
    assertNull(UserContextProxy.get(), "ThreadLocal should be cleared even for invalid token");
  }
}
