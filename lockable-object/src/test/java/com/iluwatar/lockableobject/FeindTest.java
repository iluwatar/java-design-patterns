package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Feind;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FeindTest {

  @Test
  void testBaseCase() throws InterruptedException {
    Lockable sword = new SwordOfAragorn();
    Creature orc = new Orc("Ghandar");
    Thread base = new Thread(new Feind(orc, sword));
    Assertions.assertNull(sword.getLocker());
    base.start();
    base.join();
    Assertions.assertEquals(orc, sword.getLocker());
    Creature elf = new Elf("Nagdil");
    Thread extend = new Thread(new Feind(elf, sword));
    extend.start();
    extend.join();
    Assertions.assertTrue(sword.isLocked());
  }

}
