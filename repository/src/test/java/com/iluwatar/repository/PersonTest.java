package com.iluwatar.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    public Person person = new Person("Jana", "Sucha", 22);

    @Test
    public void validateName() {
        assertNotEquals("Dana", person.getName());
        person.setName("Dana");
        assertEquals("Dana", person.getName());
    }


    @Test
    public void validateSurName() {
        assertNotEquals("Mokra", person.getSurname());
        person.setSurname("Mokra");
        assertEquals("Mokra", person.getSurname());
    }

    @Test
    public void validateAge() {
        assertNotEquals(66, person.getAge());
        person.setAge(66);
        assertEquals(66, person.getAge());
    }
}
