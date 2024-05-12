/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
