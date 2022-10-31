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
    LOGGER.info(gameObject.getName() + "'s current velocity: " + gameObject.getVelocity());
  }
}
