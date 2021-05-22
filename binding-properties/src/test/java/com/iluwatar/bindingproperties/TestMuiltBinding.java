package com.iluwatar.bindingproperties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMuiltBinding {
  /**
   * Test multiple Binding, whenever one of the binded item
   * among a, b and c changes, another one should change along with it.
   * all the other items shall change along with it.
   */
  @Test
  void testWithMultipleBinding() {
    BindableDouble a = new BindableDouble(0.0);
    BindableDouble b = new BindableDouble(0.0);
    BindableDouble c = new BindableDouble(0.0);
    BindableDouble target = new BindableDouble(0.0);
    a.bidirectionalBind(target);
    b.bidirectionalBind(target);
    c.bidirectionalBind(target);
    a.setValue(1.0);
    assertEquals(1.0, b.getValue());
    assertEquals(1.0, c.getValue());
    assertEquals(1.0, target.getValue());

    b.setValue(2.0);
    assertEquals(2.0, a.getValue());
    assertEquals(2.0, c.getValue());
    assertEquals(2.0, target.getValue());

    c.setValue(3.0);
    assertEquals(3.0, b.getValue());
    assertEquals(3.0, c.getValue());
    assertEquals(3.0, target.getValue());
  }
}
