package units;

import abstractextensions.UnitExtension;
import concreteextensions.Commander;
import java.util.Optional;

/**
 * Class defining CommanderUnit.
 */
public class CommanderUnit extends Unit {

  private CommanderExtension unitExtension;

  public CommanderUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {
    if (extensionName.equals("CommanderExtension")) {
      // Commander is now injected instead of being instantiated inside the method
      if (unitExtension == null) {
        unitExtension = new Commander(this);  // Dependency injection here
      }
      return unitExtension;
    }
    return super.getUnitExtension(extensionName);
  }
}
