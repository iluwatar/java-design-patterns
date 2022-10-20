package com.iluwatar.component.component.graphiccomponent;

import com.iluwatar.component.GameObject;

/**
 * Take this component class to update the graphic for the Game Object instance.
 */
public class ObjectGraphicComponent implements GraphicComponent {

  /**
   * The method update the graph based on the velocity of gameObject.
   *
   * @param gameObject the gameObject instance
   */
  @Override
  public void update(GameObject gameObject) {
    if (gameObject.velocity > 0) {
      System.out.println("GraphicComponent - " + gameObject.name + ": positive velocity");
    } else if (gameObject.velocity < 0) {
      System.out.println("GraphicComponent - " + gameObject.name + ": negative velocity");
    } else {
      System.out.println("GraphicComponent - " + gameObject.name + ": zero velocity");
    }
  }
}
