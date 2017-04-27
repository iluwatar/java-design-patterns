package units;

import abstractextensions.CommanderExtension;
import abstractextensions.UnitExtension;
import concreteextensions.Commander;

/**
 * Created by Srdjan on 27-Apr-17.
 */
public class CommanderUnit extends Unit {

  private CommanderExtension commanderExtension;

  public CommanderUnit(String name) {
    super(name);
  }

  @Override
  public UnitExtension getUnitExtension(String extensionName) {

    if (extensionName.equals("CommanderExtension")) {
      if (commanderExtension == null) {
        commanderExtension = new Commander(this);
      }
      return commanderExtension;
    }

    return super.getUnitExtension(extensionName);
  }
}
