package com.iluwatar.bindingproperties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMuiltBinding {
    /**
     * Test multiple Binding, whenever one of the binded item
     * among a, b and c changes, another one should change along with it.
     * all the other items shall change along with it.
     */
    @Test
    void testWithMultipleBinding(){
        BindableDouble a = new BindableDouble(0.0);
        BindableDouble b = new BindableDouble(0.0);
        BindableDouble c = new BindableDouble(0.0);
        BindableDouble target = new BindableDouble(0.0);
        a.bidirectionalBind(target);
        b.bidirectionalBind(target);
        c.bidirectionalBind(target);
        a.setValue(1.0);
        assertEquals(b.getValue(), 1.0);
        assertEquals(c.getValue(), 1.0);
        assertEquals(target.getValue(), 1.0);

        b.setValue(2.0);
        assertEquals(a.getValue(), 2.0);
        assertEquals(c.getValue(), 2.0);
        assertEquals(target.getValue(), 2.0);

        c.setValue(3.0);
        assertEquals(b.getValue(), 3.0);
        assertEquals(c.getValue(), 3.0);
        assertEquals(target.getValue(), 3.0);
    }
}
