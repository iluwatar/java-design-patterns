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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {

  private Entity entity1;
  private Entity entity2;
  private HealthComponent health1;
  private VelocityComponent velocity1;
  TransformComponent transform1 = new TransformComponent(new float[]{0.0f, 0.0f, 0.0f},
      new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
  TransformComponent transform2 = new TransformComponent(new float[]{5.0f, 0.0f, 0.0f},
      new float[]{0.0f, 45.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});

  @BeforeEach
void setUp() {
    entity1 = new Entity("Entity1");
    entity2 = new Entity("Entity2");

    entity1.addComponent(transform1);
    entity2.addComponent(transform2);

    health1 = new HealthComponent(100);
    entity1.addComponent(health1);

    velocity1 = new VelocityComponent(1.0f, 0.0f, 0.0f);
    entity1.addComponent(velocity1);
  }
  @Test
void testMain_DoesNotThrowException() {
    assertDoesNotThrow(() -> App.main(new String[]{}), "Child entity should not throw an exception if the parent is disabled.");
  }

  @Test
void testHealthComponentApplyDamage() {

    health1.applyDamage(30);

    assertEquals(70, health1.getCurrentHealth(), "Health should be reduced by 30");
  }

  @Test
void testVelocityComponentApplyForce() {

    velocity1.applyForce(0.5f, 0.0f, 0.0f);

    assertEquals(1.5f, velocity1.getVelocityX(), "Velocity X should be updated by the applied force");
    assertEquals(0.0f, velocity1.getVelocityY(), "Velocity Y should remain unchanged");
    assertEquals(0.0f, velocity1.getVelocityZ(), "Velocity Z should remain unchanged");
  }

  @Test
void testEntityUpdate() {

    float deltaTime = 1.0f / 60.0f;
    velocity1.setVelocityY(12.0f);
    float initialVelocityY = velocity1.getVelocityY();
    velocity1.update(deltaTime);

    assertTrue(velocity1.getVelocityY() > initialVelocityY, "Velocity should increase over time");
  }

  @Test
void testEntityHealthAfterDamageAndForce() {

    health1.applyDamage(40);

    velocity1.applyForce(0.2f, 0.0f, 0.0f);

    entity1.update(1.0f / 60.0f);

    assertEquals(60, health1.getCurrentHealth(), "Health should be reduced by 40");
    assertEquals(1.2f, velocity1.getVelocityX(), "Velocity X should be updated by the applied force");
  }

  @Test
void testFinalEntityStateAfterSimulation() {

    GameSystem gameSystem = new GameSystem();
    gameSystem.addEntity(entity1);
    gameSystem.addEntity(entity2);
    TransformComponent testTransform = new TransformComponent(new float[]{5.0f, 0.0f, 0.0f},
        new float[]{0.0f, 45.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
    entity1.setTransformComponent(testTransform);

    for (int i = 0; i < 10; i++) {
      gameSystem.update(1.0f / 60.0f);
      if (i == 5) {
        health1.applyDamage(30);
      }
      if (i == 3) {
        velocity1.applyForce(0.5f, 0.0f, 0.0f);
      }
    }

    assertEquals(70, health1.getCurrentHealth(), "Final health should be 70 after applying 30 damage");
    assertEquals(1.5f, velocity1.getVelocityX(), "Final velocity X should be 1.5 after applying force");
    assertNotNull(entity1.getTransformComponent(), "Entity1 should have a transform component");

  }

  @Test
void testAddTransformComponent() {
    entity1.addComponent(transform1);
    assertTrue(entity1.getComponents().contains(transform1), "Entity1 should contain the added TransformComponent.");
  }

  @Test
void testGameLoopUpdatesEntityState() {
    GameSystem gameSystem = new GameSystem();
    gameSystem.addEntity(entity1);

    for (int i = 0; i < 5; i++) {
      gameSystem.update(1.0f / 60.0f);
    }

    // Check the updated health and velocity
    assertEquals(100, health1.getCurrentHealth(), "Health should not be affected yet.");
    assertEquals(1.0f, velocity1.getVelocityX(), "Velocity X should remain the same as no force is applied.");
  }

  @Test
void testHealthReductionOverMultipleDamages() {
    health1.applyDamage(20);
    health1.applyDamage(30);

    assertEquals(50, health1.getCurrentHealth(), "Health should be reduced by 50 after two damage applications.");
  }

  @Test
void testEntityRemovalFromGameSystem() {
    GameSystem gameSystem = new GameSystem();
    gameSystem.addEntity(entity1);

    gameSystem.removeEntity(entity1);

    // Assert that entity1 is no longer in the system
    assertFalse(gameSystem.getEntities().contains(entity1), "Entity1 should no longer be in the GameSystem after removal.");
  }

  @Test
void testEntityWithoutComponents() {
    Entity entity = new Entity("EmptyEntity");
    entity.removeComponent(entity.getTransformComponent());
    assertTrue(entity.getComponents().isEmpty(), "Entity should have no components.");
    assertEquals("EmptyEntity", entity.getName(), "Entity should have the correct name.");
  }

  @Test
void testSetParentAndChildren() {
    Entity parentEntity = new Entity("ParentEntity");
    Entity childEntity = new Entity("ChildEntity");

    parentEntity.addChild(childEntity);

    assertTrue(parentEntity.getChildren().contains(childEntity), "Parent entity should contain the child entity.");
    assertEquals(parentEntity, childEntity.getParent(), "Child entity should have the parent entity set.");
  }

  @Test
void testVelocityComponentReset() {
    velocity1.applyForce(1.0f, 0.0f, 0.0f);

    velocity1.resetVelocity();

    assertEquals(1.0f, velocity1.getVelocityX(), "Velocity should be reset to its initial value.");
    assertEquals(0.0f, velocity1.getVelocityY(), "Velocity Y should remain reset.");
    assertEquals(0.0f, velocity1.getVelocityZ(), "Velocity Z should remain reset.");
  }

  @Test
void testHealthAndForceAppliedTogether() {
    health1.applyDamage(20);
    velocity1.applyForce(0.5f, 0.0f, 0.0f);

    // Update entity state after applying damage and force
    entity1.update(1.0f / 60.0f);

    assertEquals(80, health1.getCurrentHealth(), "Health should be reduced by 20 after damage.");
    assertEquals(1.5f, velocity1.getVelocityX(), "Velocity X should increase by 0.5 after applying force.");
  }
}