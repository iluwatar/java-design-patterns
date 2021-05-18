package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type Person gate way test.
 */
class PersonGateWayTest {
  /**
   * Test find.
   */
  @Test
void testFind() throws Exception {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.find(0).getFirstName());
    assertEquals("Romanoff", personGateWay.find(0).getLastName());
    assertEquals("F", personGateWay.find(0).getGender());
    assertEquals(28, personGateWay.find(0).getAge());
  }

  /**
   * Test find by first name.
   */
  @Test
void testFindByFirstName() throws Exception {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.findByFirstName("Natasha").get(0).getFirstName());
    assertEquals("Romanoff", personGateWay.findByFirstName("Natasha").get(0).getLastName());
    assertEquals("F", personGateWay.findByFirstName("Natasha").get(0).getGender());
    assertEquals(28, personGateWay.findByFirstName("Natasha").get(0).getAge());
  }

  /**
   * Test update.
   */
  @Test
void testUpdate() throws Exception {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Tony", "Stark", "M", 36);
    personGateWay.update(0, "Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.find(0).getFirstName());
    assertEquals("Romanoff", personGateWay.find(0).getLastName());
    assertEquals("F", personGateWay.find(0).getGender());
    assertEquals(28, personGateWay.find(0).getAge());
  }

  /**
   * Test insert.
   */
  @Test
void testInsert() throws Exception {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.find(0).getFirstName());
    assertEquals("Romanoff", personGateWay.find(0).getLastName());
    assertEquals("F", personGateWay.find(0).getGender());
    assertEquals(28, personGateWay.find(0).getAge());
  }

  /**
   * Test delete.
   */
  @Test
void testDelete() throws Exception {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    personGateWay.delete(0);
    assertNull(personGateWay.find(0));
  }

}
