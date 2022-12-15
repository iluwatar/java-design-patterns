package com.iluwatar.component.component.inputcomponent;

import com.iluwatar.component.GameObject;
import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * PlayerInputComponent is used to handle user key event inputs,
 * and thus it implements the InputComponent interface.
 */
@Slf4j
public class PlayerInputComponent implements InputComponent {
  private static final int WALK_ACCELERATION = 1;

  /**
   * The update method to change the velocity based on the input key event.
   *
   * @param gameObject the gameObject instance
   * @param e          key event instance
   */
  @Override
  public void update(GameObject gameObject, int e) {
    switch (e) {
      case KeyEvent.KEY_LOCATION_LEFT -> {
        gameObject.updateVelocity(-WALK_ACCELERATION);
        LOGGER.info(gameObject.getName() + " has moved left.");
      }
      case KeyEvent.KEY_LOCATION_RIGHT -> {
        gameObject.updateVelocity(WALK_ACCELERATION);
        LOGGER.info(gameObject.getName() + " has moved right.");
      }
      default -> {
        LOGGER.info(gameObject.getName() + "'s velocity is unchanged due to the invalid input");
        gameObject.updateVelocity(0);
      } // incorrect input
    }
  }
}
