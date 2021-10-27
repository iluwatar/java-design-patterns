/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.CreatureStats;
import com.iluwatar.lockableobject.domain.CreatureType;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatureTest {

  private Creature orc;
  private Creature elf;
  private Lockable sword;

  @BeforeEach
  void init() {
    elf = new Elf("Elf test");
    orc = new Orc("Orc test");
    sword = new SwordOfAragorn();
  }

  @Test
  void baseTest() {
    Assertions.assertEquals("Elf test", elf.getName());
    Assertions.assertEquals(CreatureType.ELF, elf.getType());
    Assertions.assertThrows(NullPointerException.class, () -> new Elf(null));
    Assertions.assertThrows(NullPointerException.class, () -> elf.acquire(null));
    Assertions.assertThrows(NullPointerException.class, () -> elf.attack(null));
    Assertions.assertThrows(IllegalArgumentException.class, () -> elf.hit(-10));
  }

  @Test
  void hitTest() {
    elf.hit(CreatureStats.ELF_HEALTH.getValue() / 2);
    Assertions.assertEquals(CreatureStats.ELF_HEALTH.getValue() / 2, elf.getHealth());
    elf.hit(CreatureStats.ELF_HEALTH.getValue() / 2);
    Assertions.assertFalse(elf.isAlive());

    Assertions.assertEquals(0, orc.getInstruments().size());
    Assertions.assertTrue(orc.acquire(sword));
    Assertions.assertEquals(1, orc.getInstruments().size());
    orc.kill();
    Assertions.assertEquals(0, orc.getInstruments().size());
  }

  @Test
  void testFight() throws InterruptedException {
    killCreature(elf, orc);
    Assertions.assertTrue(elf.isAlive());
    Assertions.assertFalse(orc.isAlive());
    Assertions.assertTrue(elf.getHealth() > 0);
    Assertions.assertTrue(orc.getHealth() <= 0);
  }

  @Test
  void testAcqusition() throws InterruptedException {
    Assertions.assertTrue(elf.acquire(sword));
    Assertions.assertEquals(elf.getName(), sword.getLocker().getName());
    Assertions.assertTrue(elf.getInstruments().contains(sword));
    Assertions.assertFalse(orc.acquire(sword));
    killCreature(orc, elf);
    Assertions.assertTrue(orc.acquire(sword));
    Assertions.assertEquals(orc, sword.getLocker());
  }

  void killCreature(Creature source, Creature target) throws InterruptedException {
    while (target.isAlive()) {
      source.attack(target);
    }
  }

  @Test
  void invalidDamageTest(){
    Assertions.assertThrows(IllegalArgumentException.class, () -> elf.hit(-50));
  }
}
