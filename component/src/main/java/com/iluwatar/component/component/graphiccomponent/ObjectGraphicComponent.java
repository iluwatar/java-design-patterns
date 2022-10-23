package com.iluwatar.component.component.graphiccomponent;

import com.iluwatar.component.GameObject;

/**
 * ObjectGraphicComponent class mimics the graphic component of the Game Object.
 */
public class ObjectGraphicComponent implements GraphicComponent {

  /**
   * The method updates the graphics based on the velocity of gameObject.
   *
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
    if (gameObject.velocity > 0) {
      System.out.println("GraphicComponent has been updated for: "
          + gameObject.name + ", they now have a positive velocity.");
    } else if (gameObject.velocity < 0) {
      System.out.println("GraphicComponent has been updated for: "
          + gameObject.name + ", they now have a negative velocity.");
    } else {
      System.out.println("GraphicComponent has been updated for: "
          + gameObject.name + ", their velocity is now zero.");
    }
  }
}
