package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.CreatureStats;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Human;
import com.iluwatar.lockableobject.domain.Orc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubCreaturesTests {

  @Test
  void statsTest(){
    Creature elf = new Elf("Limbar");
    Creature orc = new Orc("Dargal");
    Creature human = new Human("Jerry");
    Assertions.assertEquals(CreatureStats.ELF_HEALTH.getValue(), elf.getHealth());
    Assertions.assertEquals(CreatureStats.ELF_DAMAGE.getValue(), elf.getDamage());
    Assertions.assertEquals(CreatureStats.ORC_DAMAGE.getValue(), orc.getDamage());
    Assertions.assertEquals(CreatureStats.ORC_HEALTH.getValue(), orc.getHealth());
    Assertions.assertEquals(CreatureStats.HUMAN_DAMAGE.getValue(), human.getDamage());
    Assertions.assertEquals(CreatureStats.HUMAN_HEALTH.getValue(), human.getHealth());
  }
}
