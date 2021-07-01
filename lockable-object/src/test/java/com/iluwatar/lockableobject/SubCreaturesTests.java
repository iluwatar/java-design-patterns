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

import com.iluwatar.lockableobject.domain.CreatureStats;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Human;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubCreaturesTests {

  @Test
  void statsTest(){
    var elf = new Elf("Limbar");
    var orc = new Orc("Dargal");
    var human = new Human("Jerry");
    Assertions.assertEquals(CreatureStats.ELF_HEALTH.getValue(), elf.getHealth());
    Assertions.assertEquals(CreatureStats.ELF_DAMAGE.getValue(), elf.getDamage());
    Assertions.assertEquals(CreatureStats.ORC_DAMAGE.getValue(), orc.getDamage());
    Assertions.assertEquals(CreatureStats.ORC_HEALTH.getValue(), orc.getHealth());
    Assertions.assertEquals(CreatureStats.HUMAN_DAMAGE.getValue(), human.getDamage());
    Assertions.assertEquals(CreatureStats.HUMAN_HEALTH.getValue(), human.getHealth());
  }
}
