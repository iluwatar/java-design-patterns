package units;

import abstractextensions.CommanderExtension;
import abstractextensions.UnitExtension;
import concreteextensions.Commander;

/**
 * Created by Srdjan on 27-Apr-17.
 */
public class CommanderUnit extends Unit {

  public CommanderUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {

    if (extensionName.equals("CommanderExtension")) {
      if (unitExtension == null) {
        unitExtension = new Commander(this);
      }
      return unitExtension;
    }

    return super.getUnitExtension(extensionName);
  }
}
