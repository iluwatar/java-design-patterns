package concreteextensions;

import abstractextensions.SoldierExtension;
import units.SoldierUnit;

/**
 * Created by Srdjan on 26-Apr-17.
 */
public class Soldier implements SoldierExtension {

  private SoldierUnit unit;

  public Soldier(SoldierUnit soldierUnit) {
    this.unit = soldierUnit;
  }

  @Override
  public void soldierReady() {
    System.out.println("[Solider] " + unit.getName() + "  is ready!");
  }
}
