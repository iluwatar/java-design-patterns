package com.iluwatar.front.controller;

import org.junit.Test;

import static org.junit.Assert.*;

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