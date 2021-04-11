package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.Human;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TheSwordOfAragornTest {

  @Test
  public void basicSwordTest() {
    Lockable sword = new SwordOfAragorn();
    Assertions.assertNotNull(sword.getName());
    Assertions.assertNull(sword.getLocker());
    Assertions.assertFalse(sword.isLocked());
    Creature human = new Human("Tupac");
    Assertions.assertTrue(human.acquire(sword));
    Assertions.assertEquals(human, sword.getLocker());
    Assertions.assertTrue(sword.isLocked());
  }
}
