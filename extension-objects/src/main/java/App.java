import abstractextensions.CommanderExtension;
import abstractextensions.SergeantExtension;
import abstractextensions.SoldierExtension;
import units.CommanderUnit;
import units.SergeantUnit;
import units.SoldierUnit;
import units.Unit;

/**
 * Created by Srdjan on 26-Apr-17.
 */
public class App {

  /**
   * Program entry point
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    //Create 3 different units
    Unit unit = new SoldierUnit("SoldierUnit1");
    Unit unit1 = new SergeantUnit("SergeantUnit1");
    Unit unit2 = new CommanderUnit("CommanderUnit1");

    //check for each unit to have an extension
    checkExtensionsForUnit(unit);
    checkExtensionsForUnit(unit1);
    checkExtensionsForUnit(unit2);

  }

  private static void checkExtensionsForUnit(Unit unit) {
    //separate for better view
    System.out.println();

    SoldierExtension soldierExtension = (SoldierExtension) unit.getUnitExtension("SoldierExtension");
    SergeantExtension sergeantExtension = (SergeantExtension) unit.getUnitExtension("SergeantExtension");
    CommanderExtension commanderExtension = (CommanderExtension) unit.getUnitExtension("CommanderExtension");

    //if unit have extension call the method
    if (soldierExtension != null) {
      soldierExtension.soldierReady();
    } else {
      System.out.println(unit.getName() + " without SoldierExtension");
    }

    if (sergeantExtension != null) {
      sergeantExtension.sergeantReady();
    } else {
      System.out.println(unit.getName() + " without SergeantExtension");
    }

    if (commanderExtension != null) {
      commanderExtension.commanderReady();
    } else {
      System.out.println(unit.getName() + " without CommanderExtension");
    }
  }
}
