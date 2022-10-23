package com.iluwatar.component.component.inputcomponent;

import com.iluwatar.component.GameObject;
import java.awt.event.KeyEvent;

/**
 * PlayerInputComponent is used to handle user key event inputs, and thus it implements the InputComponent interface.
 */
public class PlayerInputComponent implements InputComponent {
  private final int walkAcceleration = 1;

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
        gameObject.velocity -= walkAcceleration;
        System.out.println("InputComponent has been updated for: " + gameObject.name + ", they have moved left.");
        break;
      case KeyEvent.KEY_LOCATION_RIGHT:
        gameObject.velocity += walkAcceleration;
        System.out.println("InputComponent has been updated for: " + gameObject.name + ", they have moved right.");
        break;
      default:
        System.out.println("InputComponent has been reset for: " + gameObject.name + ", this was an invalid input.");
        System.out.println(gameObject.name + "'s velocity and coordinates are now 0");
        gameObject.coordinate = 0;
        gameObject.velocity = 0;
        break; // incorrect input
    }
  }

  /**
   * Redundant method in the player input mode.
   *
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
  }
}
