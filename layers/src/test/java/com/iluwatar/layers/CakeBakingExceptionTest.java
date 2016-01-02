package com.iluwatar.layers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Date: 12/15/15 - 7:57 PM
 *
 * @author Jeroen Meulemeester
 */
public class CakeBakingExceptionTest {

  @Test
  public void testConstructor() throws Exception {
    final CakeBakingException exception = new CakeBakingException();
    assertNull(exception.getMessage());
    assertNull(exception.getCause());
  }

  @Test
  public void testConstructorWithMessage() throws Exception {
    final String expectedMessage = "message";
    final CakeBakingException exception = new CakeBakingException(expectedMessage);
    assertEquals(expectedMessage, exception.getMessage());
    assertNull(exception.getCause());
  }

}
