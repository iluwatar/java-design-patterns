package com.iluwatar.threadspecificstorage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

  @Test
  void process_shouldStoreAndClearUserContext() {
    // Given - a real UserContextProxy
    UserContextProxy proxy = new UserContextProxy();
    RequestHandler handler = new RequestHandler(proxy, "token::123");

    // When - process the request
    handler.process();

    // Then - after processing, ThreadLocal should be cleared
    assertNull(proxy.get(), "ThreadLocal should be cleared after process()");
  }

  @Test
  void process_withInvalidToken_shouldSetUserIdToMinusOne() {
    // Given - a real UserContextProxy
    UserContextProxy proxy = new UserContextProxy();
    RequestHandler handler = new RequestHandler(proxy, "invalid-token");

    // When - process the request
    handler.process();

    // Then - after processing, ThreadLocal should be cleared
    assertNull(proxy.get(), "ThreadLocal should be cleared even for invalid token");
  }
}
