package units;

import abstractextensions.SergeantExtension;
import abstractextensions.UnitExtension;
import concreteextensions.Sergeant;

/**
 * Created by Srdjan on 27-Apr-17.
 */
public class SergeantUnit extends Unit {

  private SergeantExtension sergeantExtension;

  public SergeantUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {

    if (extensionName.equals("SergeantExtension")) {
      if (sergeantExtension == null) {
        sergeantExtension = new Sergeant(this);
      }
      return sergeantExtension;
    }

    return super.getUnitExtension(extensionName);
  }
}
