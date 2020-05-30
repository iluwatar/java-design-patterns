package com.iluwatar.component;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

/**
 * BjornPhysicsComponent is a class for our main game star
 * This class creat a Physics component for Bjorn.
 */
public class BjornPhysicsComponent implements PhysicsComponent {

  private static final Logger LOGGER = getLogger(BjornPhysicsComponent.class);

  /**
   * This method is a logger for Bjorn when happens a Physics update.
   * In real scenario, there will be code for Physics Update.
   *
   * @param gameObject is a object in the game, here it is Bjorn
   */
  @Override
  public void update(GameObject gameObject) {
    if (gameObject.getPositionOFx() == gameObject.getPositionOFy()) {
      LOGGER.info("Your position is pretty good, keep it!");
    }
  }
}
