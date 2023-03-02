package com.iluwatar.component.component.physiccomponent;

import com.iluwatar.component.GameObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Take this component class to update the x coordinate for the Game Object instance.
 */
@Slf4j
public class ObjectPhysicComponent implements PhysicComponent {

  /**
   * The method update the horizontal (X-axis) coordinate based on the velocity of gameObject.
   *
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
    gameObject.updateCoordinate();
    LOGGER.info(gameObject.getName() + "'s coordinate has been changed.");
  }
}
