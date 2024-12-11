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
 * Abstract class representing a health component in the ECS system.
 * This component tracks the current health, max health, and whether the entity is alive.
 * Subclasses must implement the updateHealth method to define custom behavior for updating health.
 */
public class HealthComponent extends Component {

  private static final Logger logger = Logger.getLogger(HealthComponent.class.getName());

  private float currentHealth;
  private float maxHealth;
  private boolean isAlive;

  /**
   * Constructor for initializing the health component with a maximum health value.
   * The current health is set to the maximum health, and the entity is considered alive.
   *
   * @param maxHealth the maximum health of the entity
   */
  public HealthComponent(float maxHealth) {
    this.maxHealth = maxHealth;
    this.currentHealth = maxHealth;
    this.isAlive = true;
  }

  /**
   * Abstract method for updating the health component.
   * Subclasses must implement this method to define how health is updated.
   *
   * @param deltaTime the time elapsed since the last update
   */
  @Override
  public void update(float deltaTime) {
    //update health
  }

  /**
   * Applies damage to the entity. If the entity is alive, the damage is subtracted from the current health.
   * If health drops to zero or below, the entity is marked as dead.
   *
   * @param damage the amount of damage to apply
   */
  public void applyDamage(float damage) {
    if (isAlive) {
      currentHealth -= damage;
      if (currentHealth <= 0) {
        currentHealth = 0;
        isAlive = false;
      }
    }
  }

  /**
   * Heals the entity by a specified amount. If the entity is alive, the current health is increased,
   * but it cannot exceed the maximum health.
   *
   * @param amount the amount to heal
   */
  public void heal(float amount) {
    if (isAlive) {
      currentHealth += amount;
      if (currentHealth > maxHealth) {
        currentHealth = maxHealth;
      }
    }
  }

  // Setters and Getters

  public float getCurrentHealth() {
    return currentHealth;
  }

  public void setCurrentHealth(float currentHealth) {
    this.currentHealth = currentHealth;
  }

  public float getMaxHealth() {
    return maxHealth;
  }

  public void setMaxHealth(float maxHealth) {
    this.maxHealth = maxHealth;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }
}
