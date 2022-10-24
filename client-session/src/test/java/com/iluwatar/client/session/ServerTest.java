package com.iluwatar.client.session;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

  @Test
  void checkGetSession() {
    Server server = new Server("localhost", 8080);
    Session session = server.getSession("Session");
    assertEquals("Session", session.getClientName());
  }
}
