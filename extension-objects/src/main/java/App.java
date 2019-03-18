/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import abstractextensions.CommanderExtension;
import abstractextensions.SergeantExtension;
import abstractextensions.SoldierExtension;
import units.CommanderUnit;
import units.SergeantUnit;
import units.SoldierUnit;
import units.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Anticipate that an object’s interface needs to be extended in the future.
 * Additional interfaces are defined by extension objects.
 */
public class App {

  /**
   * Program entry point
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    //Create 3 different units
    Unit soldierUnit = new SoldierUnit("SoldierUnit1");
    Unit sergeantUnit = new SergeantUnit("SergeantUnit1");
    Unit commanderUnit = new CommanderUnit("CommanderUnit1");

    //check for each unit to have an extension
    checkExtensionsForUnit(soldierUnit);
    checkExtensionsForUnit(sergeantUnit);
    checkExtensionsForUnit(commanderUnit);

  }

  private static void checkExtensionsForUnit(Unit unit) {
    final Logger logger = LoggerFactory.getLogger(App.class);

    SoldierExtension soldierExtension = (SoldierExtension) unit.getUnitExtension("SoldierExtension");
    SergeantExtension sergeantExtension = (SergeantExtension) unit.getUnitExtension("SergeantExtension");
    CommanderExtension commanderExtension = (CommanderExtension) unit.getUnitExtension("CommanderExtension");

    //if unit have extension call the method
    if (soldierExtension != null) {
      soldierExtension.soldierReady();
    } else {
      logger.info(unit.getName() + " without SoldierExtension");
    }

    if (sergeantExtension != null) {
      sergeantExtension.sergeantReady();
    } else {
      logger.info(unit.getName() + " without SergeantExtension");
    }

    if (commanderExtension != null) {
      commanderExtension.commanderReady();
    } else {
      logger.info(unit.getName() + " without CommanderExtension");
    }
  }
}
