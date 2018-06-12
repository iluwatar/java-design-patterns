package com.iluwatar.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test Cases for Assertion.
 */
public class AssertionTest {
  int varX = 0;
  int varY = 0;

  @Test
  public void testAssertionTrue() {
    Assertion a = new MyAssertion();
    assertEquals(true, a.isTrue());
  }

  @Test
  public void testAssertionFalse() {
    Assertion a = new MyAssertion();
    a.check();
    varX = 1;
    assertEquals(false, a.isTrue());
  }

  @Test
  public void testAssertionError() {
    assertThrows(NullPointerException.class, () -> {
      Assertion a = new MyAssertion();
      a.check();
      varX = 1;
      a.check();
    });
  }

  class MyAssertion extends Assertion {
    public boolean isTrue() {
      return varX == varY;
    }
  }
}