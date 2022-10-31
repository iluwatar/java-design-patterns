package com.iluwatar.component.component.inputcomponent;

import com.iluwatar.component.GameObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Take this component class to control player or the NPC for demo mode.
 * and implemented the InputComponent interface.
 *
 * <p>Essentially, the demo mode is utilised during a game if the user become inactive.
 * Please see: http://gameprogrammingpatterns.com/component.html
 */
@Slf4j
public class DemoInputComponent implements InputComponent {
  private static final int WALK_ACCELERATION = 2;

  /**
   * Redundant method in the demo mode.
   *
   * @param gameObject the gameObject instance
   * @param e          key event instance
   */
  @Override
  public void update(GameObject gameObject, int e) {
    gameObject.updateVelocity(WALK_ACCELERATION);
    LOGGER.info(gameObject.getName() + " has moved right.");
  }
}
