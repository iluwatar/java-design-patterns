package com.iluwatar.poison.pill;

import org.junit.Test;

import static com.iluwatar.poison.pill.Message.Headers;
import static com.iluwatar.poison.pill.Message.POISON_PILL;

/**
 * Date: 12/27/15 - 10:30 PM
 *
 * @author Jeroen Meulemeester
 */
public class PoisonMessageTest {

  @Test(expected = UnsupportedOperationException.class)
  public void testAddHeader() throws Exception {
    POISON_PILL.addHeader(Headers.SENDER, "sender");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetHeader() throws Exception {
    POISON_PILL.getHeader(Headers.SENDER);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetHeaders() throws Exception {
    POISON_PILL.getHeaders();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSetBody() throws Exception {
    POISON_PILL.setBody("Test message.");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testGetBody() throws Exception {
    POISON_PILL.getBody();
  }

}
