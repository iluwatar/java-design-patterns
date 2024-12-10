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
 * The main entry point for the application.
 * This class simulates a game loop where entities are created, updated, and their states are modified.
 */
public class App {

  /**
   * The main method that runs the application.
   * It creates entities, adds components, updates them over several frames,
   * and demonstrates applying damage and forces to entities.
   *
   * @param args Command-line arguments (not used in this application)
   */
  public static void main(String[] args) {
    // Create example entities
    Entity entity1 = new Entity("Entity1");
    Entity entity2 = new Entity("Entity2");

    // Set up some transform components (position, rotation, scale)
    TransformComponent transform1 = new TransformComponent(new float[]{0.0f, 0.0f, 0.0f},
        new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
    TransformComponent transform2 = new TransformComponent(new float[]{5.0f, 0.0f, 0.0f},
        new float[]{0.0f, 45.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});

    // Set the transform components for each entity
    entity1.addComponent(transform1);
    entity2.addComponent(transform2);

    // Create a health component for entity1 with initial health of 100
    HealthComponent health1 = new HealthComponent(100);  // Ensure HealthComponent is implemented
    entity1.addComponent(health1);

    // Create a velocity component for entity1
    VelocityComponent velocity1 = new VelocityComponent(1.0f, 0.0f, 0.0f);
    entity1.addComponent(velocity1);

    // Set up a system and add entities to the system
    GameSystem gameSystem = new GameSystem();
    gameSystem.addEntity(entity1);
    gameSystem.addEntity(entity2);

    // Simulate game update loop (e.g., 60 FPS)
    float deltaTime = 1.0f / 60.0f; // 60 FPS

    // Simulate for a few frames
    for (int i = 0; i < 10; i++) {
      System.out.println("Frame: " + (i + 1));

      // Update all entities in the system
      gameSystem.update(deltaTime);

      // Apply some damage to entity1's health component at frame 6
      if (i == 5) {
        health1.applyDamage(30);
        System.out.println("Entity1's health after damage: " + health1.getCurrentHealth());
      }

      // Apply some force to entity1's velocity at frame 3
      if (i == 3) {
        velocity1.applyForce(0.5f, 0.0f, 0.0f);
        System.out.println("Entity1's velocity after force: (" + velocity1.getVelocityX() + ", "
            + velocity1.getVelocityY() + ", " + velocity1.getVelocityZ() + ")");
      }

      // Render the system (optional rendering logic can be added here)
      gameSystem.renderSystem();
    }

    // After the simulation, check final entity states
    System.out.println("\nFinal Entity States:");
    System.out.println("Entity1 position: " + entity1.getTransformComponent().getPosition()[0] + ", "
        + entity1.getTransformComponent().getPosition()[1] + ", "
        + entity1.getTransformComponent().getPosition()[2]);
    System.out.println("Entity1 velocity: " + velocity1.getVelocityX() + ", "
        + velocity1.getVelocityY() + ", " + velocity1.getVelocityZ());
    System.out.println("Entity1 health: " + health1.getCurrentHealth());
  }
}
