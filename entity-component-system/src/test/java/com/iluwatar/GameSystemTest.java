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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

 class GameSystemTest {

  private GameSystem gameSystem;
  private Entity entity1;
  private Entity entity2;
  private Entity childEntity;

  @BeforeEach
 void setUp() {
    gameSystem = new GameSystem();

    entity1 = new Entity("Entity1");
    entity2 = new Entity("Entity2");
    childEntity = new Entity("ChildEntity");

    entity1.addChild(childEntity);

    gameSystem.addEntity(entity1);
    gameSystem.addEntity(entity2);
  }

  @Test
 void testAddEntity() {
    assertEquals(3, gameSystem.getEntities().size(), "There should be three entities in the system.");
  }

  @Test
 void testRemoveEntity() {
    gameSystem.removeEntity(entity2);
    assertEquals(2, gameSystem.getEntities().size(), "Entity2 should be removed from the system.");
  }

  @Test
 void testGetSystemMatrix() {
    // Set a mock transform for entity1 and childEntity
    float[][] matrix1 = gameSystem.getSystemMatrix(entity1);
    float[][] matrix2 = gameSystem.getSystemMatrix(childEntity);

    assertNotNull(matrix1, "System matrix for entity1 should not be null.");
    assertNotNull(matrix2, "System matrix for childEntity should not be null.");
    assertNotEquals(matrix1, matrix2, "Matrices should be different due to different transformations.");
  }

  @Test
 void testGetSystemPosition() {
    float[] position1 = gameSystem.getSystemPosition(entity1);
    float[] position2 = gameSystem.getSystemPosition(entity2);

    assertNotNull(position1, "Position for entity1 should not be null.");
    assertNotNull(position2, "Position for entity2 should not be null.");
    assertEquals(3, position1.length, "Position should be a 3D vector.");
    assertEquals(3, position2.length, "Position should be a 3D vector.");
  }

  @Test
void testSortEntitiesByDistance() {

    float[] referencePoint = {0.0f, 0.0f, 0.0f};

    entity1.getTransformComponent().setPosition(new float[] {1.0f, 0.0f, 0.0f});
    entity2.getTransformComponent().setPosition(new float[] {3.0f, 0.0f, 0.0f});

    gameSystem.sortEntitiesByDistance(referencePoint);

    List<Entity> sortedEntities = gameSystem.getEntities();

    assertEquals(entity2, sortedEntities.get(0), "Entity2 should be closer to the reference point than Entity1.");
    assertEquals(entity1, sortedEntities.get(1), "Entity1 should be farther from the reference point than Entity2.");
  }
 }

