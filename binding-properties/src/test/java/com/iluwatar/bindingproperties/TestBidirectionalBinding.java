package com.iluwatar.bindingproperties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestBidirectionalBinding {
  /**
   * Test double Binding, whenever one of the double bind item
   * changes, another one should change along with it.
   */
  @Test
  void testWithBidirectionalBinding() {
    BindableDouble a = new BindableDouble(0.0);
    BindableDouble b = new BindableDouble(0.0);
    BindableDouble c = new BindableDouble(0.0);
    a.bidirectionalBind(b);
    c.bind(b);
    a.setValue(2.0);
    assertEquals(2.0, a.getValue());
    assertEquals(2.0,b.getValue());
    b.setValue(4.0);
    assertEquals(4.0, a.getValue());
    assertEquals(4.0, b.getValue());
    assertEquals(0.0, c.getValue());
    c.setValue(11.0);
    assertEquals(11.0, a.getValue());
    assertEquals(11.0, b.getValue());
    assertEquals(11.0, c.getValue());
  }
}
