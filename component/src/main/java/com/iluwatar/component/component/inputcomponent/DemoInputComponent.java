package com.iluwatar.component.component.inputcomponent;

import com.iluwatar.component.GameObject;


/**
 * Take this component class to control player or the NPC for demo mode.
 * and implemented the InputComponent interface.
 */
public class DemoInputComponent implements InputComponent {
  private final int walkAcceleration = 2;

  /**
   * Redundant method in the demo mode.
   *
   * @param gameObject the gameObject instance
   * @param e          key event instance
   */
  @Override
  public void update(GameObject gameObject, int e) {
  }

  /**
   * The method controls the demo instance(player/NPC) velocity automatically.
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
    gameObject.velocity += walkAcceleration;
    System.out.println("InputComponent has been updated for: " + gameObject.name + ", they have moved right.");
  }
}
