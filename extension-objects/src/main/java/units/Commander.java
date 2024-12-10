package concreteextensions;

import abstractextensions.CommanderExtension;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class defining Commander.
 */
@Slf4j
public class Commander implements CommanderExtension {
  private final CommanderUnit unit;

  // Constructor now injects CommanderUnit
  public Commander(CommanderUnit unit) {
    this.unit = unit;
  }

  @Override
  public void commanderReady() {
    LOGGER.info("[Commander] " + unit.getName() + " is ready!");
  }
}
