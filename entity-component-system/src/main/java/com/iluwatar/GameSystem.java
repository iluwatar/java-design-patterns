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

import java.util.ArrayList;
import java.util.List;

/**
 * System class that manages entities within the system.
 * It handles adding and removing entities, updating them, rendering,
 * and sorting them by distance from a reference point.
 */
public class GameSystem {

  private List<Entity> entities;

  /**
   * Constructor for initializing the system with an empty list of entities.
   */
  public GameSystem() {
    entities = new ArrayList<>();
  }

  /**
   * Adds an entity to the system, including its children recursively.
   *
   * @param entity the entity to be added
   */
  public void addEntity(Entity entity) {
    if (entity != null) {
      entities.add(entity);
      // Recursively add children entities
      for (Entity child : entity.getChildren()) {
        addEntity(child);
      }
    }
  }

  /**
   * Removes an entity from the system.
   *
   * @param entity the entity to be removed
   */
  public void removeEntity(Entity entity) {
    entities.remove(entity);
  }

  /**
   * Gets the system matrix for a specific entity by considering its transform and the transform of its parent.
   *
   * @param entity the entity whose system matrix is to be calculated
   * @return the 4x4 system transformation matrix
   */
  public float[][] getSystemMatrix(Entity entity) {
    float[][] systemMatrix = entity.getTransformComponent().getTransformMatrix();

    if (entity.getParent() != null) {
      float[][] parentMatrix = getSystemMatrix(entity.getParent());
      systemMatrix = multiplyMatrices(parentMatrix, systemMatrix);
    }

    return systemMatrix;
  }

  /**
   * Gets the system position of an entity from its transformation matrix.
   *
   * @param entity the entity whose position is to be retrieved
   * @return an array representing the entity's position [x, y, z]
   */
  public float[] getSystemPosition(Entity entity) {
    float[][] systemMatrix = getSystemMatrix(entity);
    return new float[] { systemMatrix[0][3], systemMatrix[1][3], systemMatrix[2][3] }; // Position is in the 4th column
  }

  /**
   * Updates all entities in the system by calling their update methods.
   *
   * @param deltaTime the time elapsed since the last update
   */
  public void update(float deltaTime) {
    for (Entity entity : entities) {
      entity.update(deltaTime);
    }
  }

  /**
   * Sorts the entities by their distance from a given reference point in descending order.
   *
   * @param referencePoint the point from which the distance is measured
   */
  public void sortEntitiesByDistance(float[] referencePoint) {
    entities.sort((e1, e2) -> {
      float[] pos1 = getSystemPosition(e1);
      float[] pos2 = getSystemPosition(e2);
      float distance1 = calculateDistance(pos1, referencePoint);
      float distance2 = calculateDistance(pos2, referencePoint);
      return Float.compare(distance2, distance1); // Sort in descending order
    });
  }

  public List<Entity> getEntities() {
    return entities;
  }

  /**
   * Helper method to calculate the distance between two points in 3D space.
   *
   * @param point1 the first point
   * @param point2 the second point
   * @return the distance between the two points
   */
  private float calculateDistance(float[] point1, float[] point2) {
    float dx = point1[0] - point2[0];
    float dy = point1[1] - point2[1];
    float dz = point1[2] - point2[2];
    return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  /**
   * Helper method to multiply two 4x4 matrices.
   *
   * @param matrix1 the first matrix
   * @param matrix2 the second matrix
   * @return the resulting matrix after multiplication
   */
  private float[][] multiplyMatrices(float[][] matrix1, float[][] matrix2) {
    float[][] result = new float[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        result[i][j] = 0;
        for (int k = 0; k < 4; k++) {
          result[i][j] += matrix1[i][k] * matrix2[k][j];
        }
      }
    }
    return result;
  }
}
