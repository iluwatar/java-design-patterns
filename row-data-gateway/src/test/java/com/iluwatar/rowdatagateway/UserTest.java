package com.iluwatar.rowdatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
  @Test
  void testCanEqual() {
    assertFalse((new Person(1, "San", "Zhang"))
            .canEqual("Other"));
  }

  @Test
  void testCanEqual2() {
    var Person = new Person(1, "San", "Zhang");
    assertTrue(Person.canEqual(new Person(1, "San",
            "Zhang")));
  }

  @Test
  void testEquals1() {
    var Person = new Person(1, "San", "Zhang");
    assertNotEquals("42", Person);
  }

  @Test
  void testEquals2() {
    var Person = new Person(1, "San", "Zhang");
    assertEquals(Person, new Person(1, "San",
            "Zhang"));
  }

  @Test
  void testEquals3() {
    var Person = new Person(123, "San", "Zhang");
    assertNotEquals(Person, new Person(1, "San",
            "Zhang"));
  }

  @Test
  void testEquals4() {
    var Person = new Person(1, null, "Zhang");
    assertNotEquals(Person, new Person(1, "San",
            "Zhang"));
  }

  @Test
  void testEquals5() {
    var Person = new Person(1, "Zhang", "Zhang");
    assertNotEquals(Person, new Person(1, "San",
            "Zhang"));
  }

  @Test
  void testEquals6() {
    var Person = new Person(1, "San", "San");
    assertNotEquals(Person, new Person(1, "San",
            "Zhang"));
  }

  @Test
  void testEquals7() {
    var Person = new Person(1, "San", null);
    assertNotEquals(Person, new Person(1, "San",
            "Zhang"));
  }

  @Test
  void testEquals8() {
    var Person = new Person(1, null, "Zhang");
    assertEquals(Person, new Person(1, null,
            "Zhang"));
  }

  @Test
  void testEquals9() {
    var Person = new Person(1, "San", null);
    assertEquals(Person, new Person(1, "San",
            null));
  }

  @Test
  void testHashCode1() {
    assertEquals(91410664, (new Person(1, "San",
            "Zhang")).hashCode());

  }

  @Test
  void testHashCode2() {
    assertEquals(86523281, (new Person(1, null,
            "Zhang")).hashCode());
  }

  @Test
  void testHashCode3() {
    assertEquals(5098823, (new Person(1, "San",
            null)).hashCode());
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

