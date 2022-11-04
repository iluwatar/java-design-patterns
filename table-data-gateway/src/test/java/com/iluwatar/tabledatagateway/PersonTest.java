package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

    @Test
    void testGet(){
        Person testPs = new Person(01,"YANYAN","LIU","FEMALE",80);
        assertEquals(01, testPs.getId());
        assertEquals("YANYAN",testPs.getFirstName());
        assertEquals("LIU", testPs.getLastName());
        assertEquals("FEMALE", testPs.getGender());
        assertEquals(80, testPs.getAge());
    }

    @Test
    void testSet(){
        Person testPs2 = new Person(02,"JENNIFER","YOUNG", "MALE",18);
        testPs2.setId(03);
        testPs2.setAge(20);
        testPs2.setFirstName("ALEX");
        testPs2.setLastName("JAMES");
        testPs2.setGender("MALE");
        assertEquals(03,testPs2.getId());
        assertEquals("ALEX",testPs2.getFirstName());
        assertEquals("JAMES", testPs2.getLastName());
        assertEquals("MALE", testPs2.getGender());
        assertEquals(20, testPs2.getAge());
    }
}
