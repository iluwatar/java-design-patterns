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
  private static final int walkAcceleration = 1;

  /**
   * The update method to change the velocity based on the input key event.
   *
   * @param gameObject the gameObject instance
   * @param e          key event instance
   */
  @Override
  public void update(GameObject gameObject, int e) {
    switch (e) {
      case KeyEvent.KEY_LOCATION_LEFT:
        gameObject.setVelocity(-walkAcceleration);
        LOGGER.info(gameObject.getName() + " have moved left.");
        break;
      case KeyEvent.KEY_LOCATION_RIGHT:
        gameObject.setVelocity(walkAcceleration);
        LOGGER.info(gameObject.getName() + " have moved right.");
        break;
      default:
        LOGGER.info(gameObject.getName() + "'s velocity and coordinates "
            + "are now 0 due to the invalid input");
        gameObject.setVelocity(0);
        break; // incorrect input
    }
  }
}
