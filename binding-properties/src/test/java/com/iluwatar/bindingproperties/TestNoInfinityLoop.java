package com.iluwatar.bindingproperties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestNoInfinityLoop {

    /**
     * Circulate binding should be performed correctly without being trapped
     * in an infinity loop.
     */
    @Test
    void testWithCirculateBinding(){
        BindableDouble a = new BindableDouble(0.0);
        BindableInteger b = new BindableInteger(0);
        BindableDouble c = new BindableDouble(0.0);
        BindableDouble d = new BindableDouble(0.0);
        a.bind(b, (newValue) -> (int)Math.round(newValue));
        b.bind(c, (newValue) -> (double)newValue);
        c.bind(d);
        d.bind(a);
        b.setValue(2);
        assertEquals(a.getValue(), 2.0);
        assertEquals(b.getValue(), 2);
        assertEquals(c.getValue(), 2.0);
        assertEquals(d.getValue(), 2.0);
    }

    /**
     * Self binding should be performed without any outcome
     */
    @Test
    void testWithSelfBinding(){
        BindableDouble a = new BindableDouble(3.0);
        a.bind(a, (newValue) -> 0.0);
        a.setValue(5.0);
        assertEquals(a.getValue(), 5.0);
    }

}
