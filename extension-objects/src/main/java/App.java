/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
import java.util.Optional;
import java.util.function.Function;
import org.slf4j.LoggerFactory;
import units.CommanderUnit;
import units.SergeantUnit;
import units.SoldierUnit;
import units.Unit;

/**
 * Anticipate that an object’s interface needs to be extended in the future. Additional interfaces
 * are defined by extension objects.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    //Create 3 different units
    var soldierUnit = new SoldierUnit("SoldierUnit1");
    var sergeantUnit = new SergeantUnit("SergeantUnit1");
    var commanderUnit = new CommanderUnit("CommanderUnit1");

    //check for each unit to have an extension
    checkExtensionsForUnit(soldierUnit);
    checkExtensionsForUnit(sergeantUnit);
    checkExtensionsForUnit(commanderUnit);

  }

  private static void checkExtensionsForUnit(Unit unit) {
    final var logger = LoggerFactory.getLogger(App.class);

    var name = unit.getName();
    Function<String, Runnable> func = (e) -> () -> logger.info(name + " without " + e);

    var extension = "SoldierExtension";
    Optional.ofNullable(unit.getUnitExtension(extension))
        .map(e -> (SoldierExtension) e)
        .ifPresentOrElse(SoldierExtension::soldierReady, func.apply(extension));

    extension = "SergeantExtension";
    Optional.ofNullable(unit.getUnitExtension(extension))
        .map(e -> (SergeantExtension) e)
        .ifPresentOrElse(SergeantExtension::sergeantReady, func.apply(extension));

    extension = "CommanderExtension";
    Optional.ofNullable(unit.getUnitExtension(extension))
        .map(e -> (CommanderExtension) e)
        .ifPresentOrElse(CommanderExtension::commanderReady, func.apply(extension));
  }
}
