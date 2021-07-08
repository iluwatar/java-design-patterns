package com.iluwatar.bindingproperties;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a simple example for binding properties pattern,
 * which is used to bind different properties (fields) of objects
 * together, to allow them change simultaneously.
 * Here we summon a warrior and always display his information including
 * his health point and it's remaining percentage, mana point and it's
 * remaining percentage. Everytime his HP/MP changes, its percentage
 * will also change. Whenever his HP is decreased to 0, a chance of
 * is then reduced. Therefore, we bind these properties together to
 * ensure that whenever one of the data changes, some other data should
 * change immediately according to our given rules.
 */

@Slf4j
public class App {
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
    while (me.getRemainLives() > 0) {
      me.displayMyStatus();
      log.info("I am attacked by a furious wasp! HP - {}", waspDamage);
      me.damage(waspDamage);
      me.displayMyStatus();

      log.info("I cast a spell to kill the wasp! MP - {}", spellCost);
      me.castSpell(spellCost);
      me.displayMyStatus();

      log.info("I drink healing potion! HP + {}", heal);
      me.heal(heal);
      me.displayMyStatus();

      log.info("I am attacked by a furious dragon! HP - 99999");
      me.damage(99999.0);
      me.displayMyStatus();

      if (me.getRemainLives() <= 0) {
        log.info("I am dead, no extra lives!");
      } else {
        log.info("I am dead, now I should respawn!");
        me.heal(99999.0);
        me.displayMyStatus();
      }
    }
  }
}
