package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Human;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TheSwordOfAragornTest {

  @Test
  void basicSwordTest() {
    var sword = new SwordOfAragorn();
    Assertions.assertNotNull(sword.getName());
    Assertions.assertNull(sword.getLocker());
    Assertions.assertFalse(sword.isLocked());
    var human = new Human("Tupac");
    Assertions.assertTrue(human.acquire(sword));
    Assertions.assertEquals(human, sword.getLocker());
    Assertions.assertTrue(sword.isLocked());
  }

  @Test
  void invalidLockerTest(){
    var sword = new SwordOfAragorn();
    Assertions.assertThrows(NullPointerException.class, () -> sword.lock(null));
    Assertions.assertThrows(NullPointerException.class, () -> sword.unlock(null));
  }
}
