package units;

import abstractextensions.UnitExtension;

/**
 * Created by Srdjan on 26-Apr-17.
 */
public class Unit {

  private String name;

  public Unit(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UnitExtension getUnitExtension(String extensionName) {
    return null;
  }
}
