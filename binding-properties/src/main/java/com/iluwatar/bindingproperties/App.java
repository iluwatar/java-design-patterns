package com.iluwatar.bindingproperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a simple test case for binding properties pattern.
 */

public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * The program entry point.
   *
   * @param args The command line argument. In this example it is not used.
   */
  public static void main(String[] args) {
    var me = new MyLittleWarrior(50.0, 50.0, 2);
    var waspDamage = 20.0;
    var spellCost = 10.0;
    var heal = 50.0;
    while (true) {
      me.displayMyStatus();
      LOGGER.info("I am attacked by a furious wasp! HP - {}", waspDamage);
      me.damage(waspDamage);
      me.displayMyStatus();

      LOGGER.info("I cast a spell to kill the wasp! MP - {}", spellCost);
      me.castSpell(spellCost);
      me.displayMyStatus();

      LOGGER.info("I drink healing potion! HP + {}", heal);
      me.heal(heal);
      me.displayMyStatus();

      LOGGER.info("I am attacked by a furious dragon! HP - 99999");
      me.damage(99999.0);
      me.displayMyStatus();

      if (me.getRemainLives() <= 0) {
        LOGGER.info("I am dead, no extra lives!");
        break;
      } else {
        LOGGER.info("I am dead, now I should respawn!");
        me.heal(99999.0);
        me.displayMyStatus();
      }
    }
  }
}
