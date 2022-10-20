package com.iluwatar.component;

import com.iluwatar.component.component.graphiccomponent.GraphicComponent;
import com.iluwatar.component.component.graphiccomponent.ObjectGraphicComponent;
import com.iluwatar.component.component.inputcomponent.DemoInputComponent;
import com.iluwatar.component.component.inputcomponent.InputComponent;
import com.iluwatar.component.component.inputcomponent.PlayerInputComponent;
import com.iluwatar.component.component.physiccomponent.ObjectPhysicComponent;
import com.iluwatar.component.component.physiccomponent.PhysicComponent;

/**
 * The GameObject class has three component class instances that allow
 * the creation of different game objects based on the needs.
 */
public class GameObject {
  private final InputComponent inputComponent;
  private final PhysicComponent physicComponent;
  private final GraphicComponent graphicComponent;

  public String name;
  public int velocity;
  public int coordinate;

  GameObject(InputComponent inputComponent,
             PhysicComponent physicComponent,
             GraphicComponent graphicComponent,
             String name) {
    this.inputComponent = inputComponent;
    this.physicComponent = physicComponent;
    this.graphicComponent = graphicComponent;
    this.name = name;
  }

  /**
   * Create a player game object.
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
   * Create a npc game object.
   *
   * @return npc object
   */
  public static GameObject createNpc() {
    return new GameObject(new DemoInputComponent(),
        new ObjectPhysicComponent(),
        new ObjectGraphicComponent(),
        "npc");
  }

  /**
   * update three components for demo mode.
   */
  public void demoUpdate() {
    inputComponent.update(this);
    physicComponent.update(this);
    graphicComponent.update(this);
  }

  /**
   * update three components for player mode.
   *
   * @param e key event from the player.
   */
  public void update(int e) {
    inputComponent.update(this, e);
    physicComponent.update(this);
    graphicComponent.update(this);
  }
}
