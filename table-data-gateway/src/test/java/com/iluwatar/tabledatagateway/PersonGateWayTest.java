package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type Person gate way test.
 */
public class PersonGateWayTest {
  /**
   * Test find.
   */
  @Test
  public void testFind() {
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
  public void testFindByFirstName() {
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
  public void testUpdate() {
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
  public void testInsert() {
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
  public void testDelete() {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    personGateWay.delete(0);
    assertNull(personGateWay.find(0));
  }

}
