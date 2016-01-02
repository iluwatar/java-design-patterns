package com.iluwatar.front.controller;

import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Date: 12/13/15 - 1:35 PM
 *
 * @author Jeroen Meulemeester
 */
public class ApplicationExceptionTest {

  @Test
  public void testCause() throws Exception {
    final Exception cause = new Exception();
    assertSame(cause, new ApplicationException(cause).getCause());
  }

}