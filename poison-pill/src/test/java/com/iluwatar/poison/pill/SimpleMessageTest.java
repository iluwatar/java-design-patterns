package com.iluwatar.poison.pill;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/27/15 - 10:25 PM
 *
 * @author Jeroen Meulemeester
 */
public class SimpleMessageTest {

  @Test
  public void testGetHeaders() {
    final SimpleMessage message = new SimpleMessage();
    assertNotNull(message.getHeaders());
    assertTrue(message.getHeaders().isEmpty());

    final String senderName = "test";
    message.addHeader(Message.Headers.SENDER, senderName);
    assertNotNull(message.getHeaders());
    assertFalse(message.getHeaders().isEmpty());
    assertEquals(senderName, message.getHeaders().get(Message.Headers.SENDER));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testUnModifiableHeaders() {
    final SimpleMessage message = new SimpleMessage();
    final Map<Message.Headers, String> headers = message.getHeaders();
    headers.put(Message.Headers.SENDER, "test");
  }


}