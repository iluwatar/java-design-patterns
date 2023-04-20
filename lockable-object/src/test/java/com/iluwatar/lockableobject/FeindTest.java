/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
