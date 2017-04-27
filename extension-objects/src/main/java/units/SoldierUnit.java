package units;

import abstractextensions.SoldierExtension;
import abstractextensions.UnitExtension;
import concreteextensions.Soldier;

/**
 * Created by Srdjan on 26-Apr-17.
 */
public class SoldierUnit extends Unit {

  private SoldierExtension soldierExtension;

  public SoldierUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {

    if (extensionName.equals("SoldierExtension")) {
      if (soldierExtension == null) {
        soldierExtension = new Soldier(this);
      }

      return soldierExtension;
    }
    return super.getUnitExtension(extensionName);
  }
}
