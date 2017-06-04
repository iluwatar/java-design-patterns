package concreteextensions;

import abstractextensions.CommanderExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import units.CommanderUnit;

/**
 * Created by Srdjan on 27-Apr-17.
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
