package concreteextensions;

import abstractextensions.CommanderExtension;
import units.CommanderUnit;

/**
 * Created by Srdjan on 27-Apr-17.
 */
public class Commander implements CommanderExtension {

  private CommanderUnit unit;

  public Commander(CommanderUnit commanderUnit) {
    this.unit = commanderUnit;
  }

  @Override
  public void commanderReady() {
    System.out.println("[Commander] " + unit.getName() + " is ready!");
  }
}
