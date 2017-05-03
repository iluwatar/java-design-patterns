package units;

import abstractextensions.UnitExtension;
import concreteextensions.Soldier;

/**
 * Created by Srdjan on 26-Apr-17.
 */
public class SoldierUnit extends Unit {

  public SoldierUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {

    if (extensionName.equals("SoldierExtension")) {
      if (unitExtension == null) {
        unitExtension = new Soldier(this);
      }

      return unitExtension;
    }
    return super.getUnitExtension(extensionName);
  }
}
