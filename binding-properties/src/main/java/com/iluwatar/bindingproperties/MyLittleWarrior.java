package com.iluwatar.bindingproperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We have a little warrior that has HP, MP (for casting spells)
 * and some remaining lives. Every time the little warrior's HP is healed
 * or damaged, he will gain the same amount of MP. Whenever the HP is lower
 * than 0, he must respawn himself until he does not have remaining lives.
 * Besides, we need to display his HP and MP percentages on the screen.
 * Now his class contains binding properties to implement those features.
 */
public class MyLittleWarrior {
  private BindableInteger remainLives;
  private BindableDouble myHp;
  private BindableDouble myMp;
  private BindableDouble myHpPercentage;
  private BindableDouble myMpPercentage;
  private double maxHp;
  private double maxMp;
  private static final Logger LOGGER = LoggerFactory.getLogger(MyLittleWarrior.class);

  /**
   * Spawn the warrior, initiate his HP and MP to max values.
   */
  public MyLittleWarrior(double maxHp, double maxMp, int remain) {
    this.maxHp = maxHp;
    this.maxMp = maxMp;
    remainLives = new BindableInteger(remain);
    myHp = new BindableDouble(maxHp);
    myMp = new BindableDouble(maxMp);
    myHpPercentage = new BindableDouble(100.0);
    myMpPercentage = new BindableDouble(100.0);
    myHp.bind(myMp,
        newHp -> Math.min(Math.max(myMp.getValue() + (newHp - myHp.getValue()), 0), maxMp));
    myHp.bidirectionalBind(myHpPercentage,
        newHp -> Math.min(Math.max((newHp / maxHp) * 100.0, 0.0), 100.0),
        newPercentage -> Math.min(Math.max(newPercentage / 100.0 * maxHp, 0.0), 50.0));
    myMp.bidirectionalBind(myMpPercentage,
        newMp -> Math.min(Math.max((newMp / maxMp) * 100.0, 0.0), 100.0),
        newPercentage -> Math.min(Math.max(newPercentage / 100.0 * maxMp, 0.0), 50.0));
    myHp.bind(remainLives,
        newHp -> newHp <= 0.0 ? remainLives.getValue() - 1 : remainLives.getValue());
  }

  /**
   * Show the warrior's current status.
   */
  public void displayMyStatus() {
    LOGGER.info("HP:{}  MP:{}  HP percentage:{}  MP percentage:{}  Remaining Lives:{}",
            myHp.getValue(), myMp.getValue(),
            myHpPercentage.getValue(), myMpPercentage.getValue(), remainLives.getValue());
  }

  /**
   * Deal some damage to the warrior.
   */
  public void damage(double value) {
    myHp.setValue(Math.min(Math.max(myHp.getValue() - value, 0), maxHp));
  }

  /**
   * Heal the warrior.
   */
  public void heal(double value) {
    myHp.setValue(Math.min(Math.max(myHp.getValue() + value, 0), maxHp));
  }

  /**
   * Use some MP to cast a spell.
   */
  public void castSpell(double cost) {
    myMp.setValue(Math.min(Math.max(myMp.getValue() - cost, 0), maxMp));
  }

  /**
   * Check the remaining lives of the warrior.
   */
  public int getRemainLives() {
    return remainLives.getValue();
  }
}
