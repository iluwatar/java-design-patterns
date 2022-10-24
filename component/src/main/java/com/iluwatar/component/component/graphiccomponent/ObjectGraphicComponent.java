package com.iluwatar.component.component.graphiccomponent;

import com.iluwatar.component.GameObject;
import lombok.extern.slf4j.Slf4j;

/**
 * ObjectGraphicComponent class mimics the graphic component of the Game Object.
 */
@Slf4j
public class ObjectGraphicComponent implements GraphicComponent {

  /**
   * The method updates the graphics based on the velocity of gameObject.
   *
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
    if (gameObject.getVelocity() > 0) {
      LOGGER.info(gameObject.getName() + " have a positive velocity.");
    } else if (gameObject.getVelocity() < 0) {
      LOGGER.info(gameObject.getName() + " have a negative velocity.");
    } else {
      LOGGER.info(gameObject.getName() + " have zero velocity.");
    }
  }
}
