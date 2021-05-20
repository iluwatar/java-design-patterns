package com.iluwatar.rowdatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  @Test
  void testEquals() {
    var Person = new Person(1, "San", "Zhang");
    assertNotEquals("42", Person);
  }

  @Test
  void testHashCode() {
    assertEquals(91410664, (new Person(1, "San",
            "Zhang")).hashCode());
  }

  @Test
  void testSetId() {
    var Person = new Person(1, "San", "Zhang");
    Person.setId(2);
    assertEquals(2, Person.getId());
  }

  @Test
  void testSetFirstName() {
    var Person = new Person(1, "San", "tmp");
    Person.setFirstName("Zhang");
    assertEquals("Zhang", Person.getFirstName());
  }

  @Test
  void testSetLastName() {
    var Person = new Person(1, "tmp", "Zhang");
    Person.setLastName("San");
    assertEquals("San", Person.getLastName());
  }

  @Test
  void testToString() {
    var Person = new Person(1, "San", "Zhang");
    assertEquals(String.format("Person(id=%s, firstName=%s, lastName=%s)",
            Person.getId(), Person.getFirstName(), Person.getLastName()),
            Person.toString());
  }
}

