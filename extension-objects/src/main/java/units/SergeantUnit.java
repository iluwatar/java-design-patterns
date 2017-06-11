package units;

import abstractextensions.UnitExtension;
import concreteextensions.Sergeant;

/**
 * Class defining SergeantUnit
 */
public class SergeantUnit extends Unit {

  public SergeantUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {

    if (extensionName.equals("SergeantExtension")) {
      if (unitExtension == null) {
        unitExtension = new Sergeant(this);
      }
      return unitExtension;
    }

    return super.getUnitExtension(extensionName);
  }
}
