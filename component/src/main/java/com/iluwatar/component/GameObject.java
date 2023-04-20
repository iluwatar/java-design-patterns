package com.iluwatar.component;

import com.iluwatar.component.component.graphiccomponent.GraphicComponent;
import com.iluwatar.component.component.graphiccomponent.ObjectGraphicComponent;
import com.iluwatar.component.component.inputcomponent.DemoInputComponent;
import com.iluwatar.component.component.inputcomponent.InputComponent;
import com.iluwatar.component.component.inputcomponent.PlayerInputComponent;
import com.iluwatar.component.component.physiccomponent.ObjectPhysicComponent;
import com.iluwatar.component.component.physiccomponent.PhysicComponent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The GameObject class has three component class instances that allow
 * the creation of different game objects based on the game design requirements.
 */
@Getter
@RequiredArgsConstructor
public class GameObject {
  private final InputComponent inputComponent;
  private final PhysicComponent physicComponent;
  private final GraphicComponent graphicComponent;

  private final String name;
  private int velocity = 0;
  private int coordinate = 0;

  /**
   * Creates a player game object.
   *
   * @return player object
   */
  public static GameObject createPlayer() {
    return new GameObject(new PlayerInputComponent(),
        new ObjectPhysicComponent(),
        new ObjectGraphicComponent(),
        "player");
  }


  /**
   * Creates a NPC game object.
   *
   * @return npc object
   */
  public static GameObject createNpc() {
    return new GameObject(
            new DemoInputComponent(),
        new ObjectPhysicComponent(),
        new ObjectGraphicComponent(),
        "npc");
  }

  /**
   * Updates the three components of the NPC object used in the demo in App.java
   * note that this is simply a duplicate of update() without the key event for
   * demonstration purposes.
   *
   * <p>This method is usually used in games if the player becomes inactive.
   */
  public void demoUpdate() {
    inputComponent.update(this, 0);
    physicComponent.update(this);
    graphicComponent.update(this);
  }

  /**
   * Updates the three components for objects based on key events.
   *
   * @param e key event from the player.
   */
  public void update(int e) {
    inputComponent.update(this, e);
    physicComponent.update(this);
    graphicComponent.update(this);
  }

  /**
   * Update the velocity based on the acceleration of the GameObject.
   *
   * @param acceleration the acceleration of the GameObject
   */
  public void updateVelocity(int acceleration) {
    this.velocity += acceleration;
  }


  /**
   * Set the c based on the current velocity.
   */
  public void updateCoordinate() {
    this.coordinate += this.velocity;
  }
}
