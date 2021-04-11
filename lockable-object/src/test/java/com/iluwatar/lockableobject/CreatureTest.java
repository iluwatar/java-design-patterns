package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreatureTest {

  @Test
  public void testFight(){
    Creature elf = new Elf("Elf test");
    Creature orc = new Orc("Orc test");
    killCreature(elf, orc);
    Assertions.assertTrue(elf.isAlive());
    Assertions.assertFalse(orc.isAlive());
    Assertions.assertTrue(elf.getHealth() > 0);
    Assertions.assertTrue(orc.getHealth() <= 0);
  }
  
  @Test
  public void testAcqusition(){
    Creature elf = new Elf("Elf test");
    Creature orc = new Orc("Orc test");
    Lockable sword = new SwordOfAragorn();
    Assertions.assertTrue(elf.acquire(sword));
    Assertions.assertEquals(elf.getName(),sword.getLocker().getName());
    Assertions.assertFalse(orc.acquire(sword));
    killCreature(orc, elf);
    Assertions.assertTrue(orc.acquire(sword));
    Assertions.assertEquals(orc, sword.getLocker());
  }

  private void killCreature(Creature source, Creature target){
    while(target.isAlive()){
      source.attack(target);
    }
  }

}
