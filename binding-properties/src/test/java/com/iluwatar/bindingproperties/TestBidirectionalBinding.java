package com.iluwatar.bindingproperties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBidirectionalBinding {

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
    assertEquals(a.getValue(), 2.0);
    assertEquals(b.getValue(), 2.0);
    b.setValue(4.0);
    assertEquals(a.getValue(), 4.0);
    assertEquals(b.getValue(), 4.0);
    assertEquals(c.getValue(), 0.0);
    c.setValue(11.0);
    assertEquals(a.getValue(), 11.0);
    assertEquals(b.getValue(), 11.0);
    assertEquals(c.getValue(), 11.0);
  }
}
