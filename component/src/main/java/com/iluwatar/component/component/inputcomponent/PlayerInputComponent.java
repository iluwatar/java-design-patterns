package com.iluwatar.component.component.inputcomponent;

import com.iluwatar.component.GameObject;

import java.awt.event.KeyEvent;

/**
 * Take this component class for handling user input and implemented.
 * the InputComponent interface.
 */
public class PlayerInputComponent implements InputComponent {
  private final int walkAcceleration = 1;

  /**
   * The update method to change the velocity based on the input e.
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
   * Useless method in the player input mode.
   *
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
  }
}
