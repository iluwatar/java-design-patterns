package com.iluwatar.bindingproperties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class TestChainBinding {
  /**
   * Chained binding should be performed with changes correctly
   * propagated.
   */
  @Test
  void testWithChain() {
    BindableDouble a = new BindableDouble(0.0);
    BindableDouble b = new BindableDouble(0.0);
    BindableDouble c = new BindableDouble(0.0);
    BindableDouble d = new BindableDouble(0.0);

    a.bind(b);
    b.bind(c);
    c.bind(d);

    a.setValue(2.0);
    assertEquals(a.getValue(), 2.0);
    assertEquals(b.getValue(), 2.0);
    assertEquals(c.getValue(), 2.0);
    assertEquals(d.getValue(), 2.0);
  }

  /**
   * Chained binding should be performed with changes correctly
   * propagated.
   */
  @Test
  void testWithMixedChain() {
    BindableDouble a = new BindableDouble(0.0);
    BindableInteger b = new BindableInteger(0);
    BindableDouble c = new BindableDouble(0.0);
    BindableDouble d = new BindableDouble(0.0);

    a.bind(b, (newValue) -> (int)Math.round(newValue));
    b.bind(c, (newValue) -> (double)newValue);
    c.bind(d);

    a.setValue(2.0);
    assertEquals(a.getValue(), 2.0);
    assertEquals(b.getValue(), 2);
    assertEquals(c.getValue(), 2.0);
    assertEquals(d.getValue(), 2.0);
  }
}
