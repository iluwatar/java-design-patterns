package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Feind;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeindTest {

  private Creature elf;
  private Creature orc;
  private Lockable sword;

  @BeforeEach
  void init(){
    elf = new Elf("Nagdil");
    orc = new Orc("Ghandar");
    sword = new SwordOfAragorn();
  }

  @Test
  void nullTests(){
    Assertions.assertThrows(NullPointerException.class, () -> new Feind(null, null));
    Assertions.assertThrows(NullPointerException.class, () -> new Feind(elf, null));
    Assertions.assertThrows(NullPointerException.class, () -> new Feind(null, sword));
  }

  @Test
  void testBaseCase() throws InterruptedException {
    var base = new Thread(new Feind(orc, sword));
    Assertions.assertNull(sword.getLocker());
    base.start();
    base.join();
    Assertions.assertEquals(orc, sword.getLocker());
    var extend = new Thread(new Feind(elf, sword));
    extend.start();
    extend.join();
    Assertions.assertTrue(sword.isLocked());

    sword.unlock(elf.isAlive() ? elf : orc);
    Assertions.assertNull(sword.getLocker());
  }

}
