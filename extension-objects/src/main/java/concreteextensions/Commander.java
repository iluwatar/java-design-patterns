package concreteextensions;

import abstractextensions.CommanderExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import units.CommanderUnit;

/**
 * Class defining Commander
 */
public class Commander implements CommanderExtension {

  private CommanderUnit unit;

  public Commander(CommanderUnit commanderUnit) {
    this.unit = commanderUnit;
  }

  final Logger logger = LoggerFactory.getLogger(Commander.class);

  @Override
  public void commanderReady() {
    logger.info("[Commander] " + unit.getName() + " is ready!");
  }
}
