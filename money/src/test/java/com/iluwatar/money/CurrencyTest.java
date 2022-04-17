package com.iluwatar.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {
  @Test
  public void testCurrencyCentFactor() {
    assertEquals(100, Currency.USD.getCentFactor());
  }

  @Test
  public void testCurrencyRepresentation() {
    assertEquals("USD", Currency.USD.getStringRepresentation());
  }
}
