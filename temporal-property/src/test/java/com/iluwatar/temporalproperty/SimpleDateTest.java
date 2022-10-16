package com.iluwatar.temporalproperty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleDateTest {

    @BeforeEach
    public void setup(){
        SimpleDate.setToday(new SimpleDate(0,0,0));
    }

    @Test
    public void todaySetCorrectly() {
        SimpleDate newToday = new SimpleDate(1,2,3);

        SimpleDate.setToday(newToday);
        assertEquals(newToday, SimpleDate.getToday());
    }

    @Test
    public void dateComparison(){
        SimpleDate less = new SimpleDate(1,2,3);
        SimpleDate middle = new SimpleDate(2, 3, 1);
        SimpleDate equalMiddle = new SimpleDate(2, 3, 1);
        SimpleDate more = new SimpleDate(3, 1, 2);

        assertEquals(0, middle.compareTo(equalMiddle));

        assertEquals(-1, less.compareTo(middle));
        assertEquals(-1, less.compareTo(more));

        assertEquals(-1, middle.compareTo(more));
        assertEquals(1, middle.compareTo(less));

        assertEquals(1, more.compareTo(less));
        assertEquals(1, more.compareTo(middle));

        assertEquals(1, new SimpleDate(1,0,0).compareTo(new SimpleDate(0,0,0)));
        assertEquals(-1, new SimpleDate(0,0,0).compareTo(new SimpleDate(1,0,0)));

        assertEquals(1, new SimpleDate(0,1,0).compareTo(new SimpleDate(0,0,0)));
        assertEquals(-1, new SimpleDate(0,0,0).compareTo(new SimpleDate(0,1,0)));

        assertEquals(1, new SimpleDate(0,0,1).compareTo(new SimpleDate(0,0,0)));
        assertEquals(-1, new SimpleDate(0,0,0).compareTo(new SimpleDate(0,0,1)));

        assertNotEquals(null, less);
    }
}
