package com.iluwatar.component.component.physiccomponent;

import com.iluwatar.component.GameObject;

/**
 * Take this component class to update the x coordinate for the Game Object instance.
 */
public class ObjectPhysicComponent implements PhysicComponent {

  /**
   * The method update the horizontal (X-axis) coordinate based on the velocity of gameObject.
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
    gameObject.coordinate += gameObject.velocity;
    System.out.println("PhysicComponent has been updated for: " + gameObject.name + ", their coordinates have changed.");
  }
}
