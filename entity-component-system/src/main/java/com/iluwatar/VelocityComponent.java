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

import java.util.logging.Logger;

/**
 * A component that handles the velocity of an entity in 3D space.
 * It allows updating velocity, applying forces, and friction.
 */
public class VelocityComponent extends Component {

  private static final Logger logger = Logger.getLogger(VelocityComponent.class.getName());

  private float velocityX;  // The velocity in the X direction
  private float velocityY;  // The velocity in the Y direction
  private float velocityZ;  // The velocity in the Z direction

  /**
   * Constructs a VelocityComponent with the given velocity values in the X, Y, and Z directions.
   *
   * @param velocityX the initial velocity in the X direction
   * @param velocityY the initial velocity in the Y direction
   * @param velocityZ the initial velocity in the Z direction
   */
  public VelocityComponent(float velocityX, float velocityY, float velocityZ) {
    this.velocityX = velocityX;
    this.velocityY = velocityY;
    this.velocityZ = velocityZ;
  }

  /**
   * Updates the velocity of the entity based on delta time.
   * This method multiplies the current velocity by the delta time to simulate movement.
   *
   * @param deltaTime the time elapsed since the last update
   */
  public void update(float deltaTime) {
    velocityX += velocityX * deltaTime;
    velocityY += velocityY * deltaTime;
    velocityZ += velocityZ * deltaTime;

  }

  /**
   * Applies a force to the entity, updating its velocity.
   * The force values will be added to the current velocity.
   *
   * @param forceX the force applied in the X direction
   * @param forceY the force applied in the Y direction
   * @param forceZ the force applied in the Z direction
   */
  public void applyForce(float forceX, float forceY, float forceZ) {
    this.velocityX += forceX;
    this.velocityY += forceY;
    this.velocityZ += forceZ;
  }

  /**
   * Applies friction to the entity's velocity. The velocity is reduced by the friction coefficient.
   * A friction coefficient of 0 means no friction, and 1 means full friction (stopping the entity).
   *
   * @param frictionCoefficient the coefficient of friction applied to the velocity
   */
  public void applyFriction(float frictionCoefficient) {
    this.velocityX *= (1 - frictionCoefficient);
    this.velocityY *= (1 - frictionCoefficient);
    this.velocityZ *= (1 - frictionCoefficient);
  }

  // Getters and setters for velocityX, velocityY, and velocityZ

  /**
   * Returns the velocity in the X direction.
   *
   * @return the velocity in the X direction
   */
  public float getVelocityX() {
    return velocityX;
  }

  /**
   * Sets the velocity in the X direction.
   *
   * @param velocityX the velocity in the X direction
   */
  public void setVelocityX(float velocityX) {
    this.velocityX = velocityX;
  }

  /**
   * Returns the velocity in the Y direction.
   *
   * @return the velocity in the Y direction
   */
  public float getVelocityY() {
    return velocityY;
  }

  /**
   * Sets the velocity in the Y direction.
   *
   * @param velocityY the velocity in the Y direction
   */
  public void setVelocityY(float velocityY) {
    this.velocityY = velocityY;
  }

  /**
   * Returns the velocity in the Z direction.
   *
   * @return the velocity in the Z direction
   */
  public float getVelocityZ() {
    return velocityZ;
  }

  /**
   * Sets the velocity in the Z direction.
   *
   * @param velocityZ the velocity in the Z direction
   */
  public void setVelocityZ(float velocityZ) {
    this.velocityZ = velocityZ;
  }
}
