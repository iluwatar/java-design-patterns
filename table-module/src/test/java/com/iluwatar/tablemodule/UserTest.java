package com.iluwatar.tablemodule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
  @Test
  public void testCanEqual() {
    assertFalse((new User(1, "janedoe", "iloveyou"))
            .canEqual("Other"));
  }

  @Test
  public void testCanEqual2() {
    var user = new User(1, "janedoe", "iloveyou");
    assertTrue(user.canEqual(new User(1, "janedoe",
            "iloveyou")));
  }

  @Test
  public void testEquals1() {
    var user = new User(1, "janedoe", "iloveyou");
    assertNotEquals("42", user);
  }

  @Test
  public void testEquals2() {
    var user = new User(1, "janedoe", "iloveyou");
    assertEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  public void testEquals3() {
    var user = new User(123, "janedoe", "iloveyou");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  public void testEquals4() {
    var user = new User(1, null, "iloveyou");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  public void testEquals5() {
    var user = new User(1, "iloveyou", "iloveyou");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  public void testEquals6() {
    var user = new User(1, "janedoe", "janedoe");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  public void testEquals7() {
    var user = new User(1, "janedoe", null);
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  public void testEquals8() {
    var user = new User(1, null, "iloveyou");
    assertEquals(user, new User(1, null,
            "iloveyou"));
  }

  @Test
  public void testEquals9() {
    var user = new User(1, "janedoe", null);
    assertEquals(user, new User(1, "janedoe",
            null));
  }

  @Test
  public void testHashCode1() {
    assertEquals(-1758941372, (new User(1, "janedoe",
            "iloveyou")).hashCode());

  }

  @Test
  public void testHashCode2() {
    assertEquals(-1332207447, (new User(1, null,
            "iloveyou")).hashCode());
  }

  @Test
  public void testHashCode3() {
    assertEquals(-426522485, (new User(1, "janedoe",
            null)).hashCode());
  }

  @Test
  public void testSetId() {
    var user = new User(1, "janedoe", "iloveyou");
    user.setId(2);
    assertEquals(2, user.getId());
  }

  @Test
  public void testSetPassword() {
    var user = new User(1, "janedoe", "tmp");
    user.setPassword("iloveyou");
    assertEquals("iloveyou", user.getPassword());
  }

  @Test
  public void testSetUsername() {
    var user = new User(1, "tmp", "iloveyou");
    user.setUsername("janedoe");
    assertEquals("janedoe", user.getUsername());
  }

  @Test
  public void testToString() {
    var user = new User(1, "janedoe", "iloveyou");
    assertEquals(String.format("User(id=%s, username=%s, password=%s)",
            user.getId(), user.getUsername(), user.getPassword()),
            user.toString());
  }
}

