package com.iluwatar.adapter;

/**
 * The Captain uses {@link BattleShip} to fight. <br>
 * This is the client in the pattern.
 */
public class Captain implements BattleShip {

  private BattleShip battleship;

  public Captain() {

  }

  public Captain(BattleShip battleship) {
    this.battleship = battleship;
  }

  public void setBattleship(BattleShip battleship) {
    this.battleship = battleship;
  }

  @Override
  public void fire() {
    battleship.fire();
  }

  @Override
  public void move() {
    battleship.move();
  }

}
