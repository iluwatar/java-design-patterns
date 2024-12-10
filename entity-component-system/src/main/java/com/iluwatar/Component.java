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
package com.iluwatar;

/**
 * Abstract class representing a component in the ECS system.
 * Each component can be enabled or disabled and associated with an entity.
 * Subclasses of this class must implement the update method.
 */
public abstract class Component {

  private String name;
  private boolean isEnabled;
  private Entity parent;

  /**
   * Default constructor for the component.
   * Initializes a new component with default values.
   */
  public Component() {
    // Constructor left empty intentionally, no specific initialization required
  }

  /**
   * Gets the name of the component.
   *
   * @return the name of the component
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the component.
   *
   * @param name the name of the component
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the enabled state of the component.
   *
   * @return true if the component is enabled, false otherwise
   */
  public boolean getEnabled() {
    return isEnabled;
  }

  /**
   * Sets the enabled state of the component.
   *
   * @param isEnabled true to enable the component, false to disable it
   */
  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /**
   * Gets the parent entity of this component.
   *
   * @return the parent entity
   */
  public Entity getParent() {
    return parent;
  }

  /**
   * Sets the parent entity of this component.
   *
   * @param parent the parent entity to set
   */
  public void setParent(Entity parent) {
    this.parent = parent;
  }

  /**
   * Abstract method to update the component.
   * Subclasses must implement this method to define their update behavior.
   *
   * @param deltaTime the time elapsed since the last update
   */
  public abstract void update(float deltaTime);
}
