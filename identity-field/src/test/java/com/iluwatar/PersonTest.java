package com.iluwatar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

  @Test
  void testGetId() {
    Person person = new Person();
    person.setId(1L);

    assertEquals(1L, person.getId());
  }

  @Test
  void testSetId() {
    Person person = new Person();
    person.setId(1L);

    assertEquals(1L, person.getId());

    person.setId(2L);

    assertEquals(2L, person.getId());
  }

  @Test
  void testGetName() {
    Person person = new Person("Alice", 25);

    assertEquals("Alice", person.getName());
  }

  @Test
  void testSetName() {
    Person person = new Person("Alice", 25);

    assertEquals("Alice", person.getName());

    person.setName("Bob");

    assertEquals("Bob", person.getName());
  }

  @Test
  void testGetAge() {
    Person person = new Person("Alice", 25);

    assertEquals(25, person.getAge());
  }

  @Test
  void testSetAge() {
    Person person = new Person("Alice", 25);

    assertEquals(25, person.getAge());

    person.setAge(30);

    assertEquals(30, person.getAge());
  }

  @Test
  void testConstructor() {
    Person person = new Person("Alice", 25);

    assertEquals("Alice", person.getName());
    assertEquals(25, person.getAge());
  }
}