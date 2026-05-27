package com.iluwatar.immutable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImmutableObjectTest {

    @Test
    void testObjectCreation() {
        ImmutableObject obj = 
            new ImmutableObject("Prashant", 22, "p@gmail.com");
        
        assertEquals("Prashant", obj.getName());
        assertEquals(22, obj.getAge());
        assertEquals("p@gmail.com", obj.getEmail());
    }

    @Test
    void testToString() {
        ImmutableObject obj = 
            new ImmutableObject("Prashant", 22, "p@gmail.com");
        
        assertTrue(obj.toString().contains("Prashant"));
    }

    @Test
    void testImmutability() {
        ImmutableObject obj1 = 
            new ImmutableObject("A", 1, "a@gmail.com");
        ImmutableObject obj2 = 
            new ImmutableObject("A", 1, "a@gmail.com");
        
        assertNotSame(obj1, obj2);
    }
    
    @Test
    void testEquality() {
        ImmutableObject obj1 = 
            new ImmutableObject("A", 1, "a@gmail.com");
        ImmutableObject obj2 = 
            new ImmutableObject("A", 1, "a@gmail.com");
        
        assertEquals(obj1, obj2);
    }

    @Test
    void testNullName() {
        assertThrows(NullPointerException.class, () -> {
            new ImmutableObject(null, 1, "a@gmail.com");
        });
    }
}