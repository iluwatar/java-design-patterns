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
 * A component that handles the transformation of an entity in 3D space.
 * This includes position, scale, and rotation (represented by Euler angles).
 */
public class TransformComponent extends Component {

  private float[] position;  // Position of the entity (x, y, z)
  private float[] scale;     // Scale of the entity (x, y, z)
  private float[] rotation;  // Rotation of the entity (pitch, yaw, roll)

  /**
   * Constructs a TransformComponent with the specified position, rotation, and scale.
   *
   * @param initPosition the initial position of the entity
   * @param initRotation the initial rotation (Euler angles: pitch, yaw, roll)
   * @param initScale    the initial scale of the entity
   */
  public TransformComponent(float[] initPosition, float[] initRotation, float[] initScale) {
    super();
    this.position = initPosition != null ? initPosition : new float[]{0.0f, 0.0f, 0.0f};
    this.scale = initScale != null ? initScale : new float[]{1.0f, 1.0f, 1.0f};
    this.rotation = initRotation != null ? initRotation : new float[]{0.0f, 0.0f, 0.0f};
  }

  /**
   * Default constructor that initializes the transform component with default values.
   */
  public TransformComponent() {
    this(new float[]{0.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
  }

  /**
   * Updates the transform component (add any transformations here if needed).
   *
   * @param deltaTime the time elapsed since the last update
   */
  @Override
  public void update(float deltaTime) {
    // No specific updates in this implementation
  }

  /**
   * Returns the 4x4 transformation matrix that represents the position, scale, and rotation of the entity.
   *
   * @return a 4x4 transformation matrix
   */
  public float[][] getTransformMatrix() {
    float[][] matrix = new float[4][4];

    // Initialize as an identity matrix
    matrix[0][0] = 1.0f;
    matrix[0][1] = 0.0f;
    matrix[0][2] = 0.0f;
    matrix[0][3] = 0.0f;

    matrix[1][0] = 0.0f;
    matrix[1][1] = 1.0f;
    matrix[1][2] = 0.0f;
    matrix[1][3] = 0.0f;

    matrix[2][0] = 0.0f;
    matrix[2][1] = 0.0f;
    matrix[2][2] = 1.0f;
    matrix[2][3] = 0.0f;

    matrix[3][0] = 0.0f;
    matrix[3][1] = 0.0f;
    matrix[3][2] = 0.0f;
    matrix[3][3] = 1.0f;

    // Apply scaling
    matrix[0][0] *= scale[0];
    matrix[1][1] *= scale[1];
    matrix[2][2] *= scale[2];

    // Apply rotation (pitch, yaw, roll)
    applyRotation(matrix);

    // Apply translation (position)
    matrix[0][3] = position[0];
    matrix[1][3] = position[1];
    matrix[2][3] = position[2];

    return matrix;
  }

  /**
   * Applies the rotation to the transformation matrix based on the Euler angles.
   *
   * @param matrix the transformation matrix to be modified
   */
  private void applyRotation(float[][] matrix) {
    // Matrix transformations using rotation directly
    matrix[1][1] = (float) Math.cos(rotation[0]);  // pitch
    matrix[1][2] = (float) -Math.sin(rotation[0]);
    matrix[2][1] = (float) Math.sin(rotation[0]);
    matrix[2][2] = (float) Math.cos(rotation[0]);

    matrix[0][0] = (float) Math.cos(rotation[1]);  // yaw
    matrix[0][2] = (float) Math.sin(rotation[1]);
    matrix[2][0] = (float) -Math.sin(rotation[1]);
    matrix[2][2] = (float) Math.cos(rotation[1]);

    matrix[0][0] = (float) Math.cos(rotation[2]);  // roll
    matrix[0][1] = (float) -Math.sin(rotation[2]);
    matrix[1][0] = (float) Math.sin(rotation[2]);
    matrix[1][1] = (float) Math.cos(rotation[2]);
  }

  /**
   * Sets the rotation of the entity using Euler angles (pitch, yaw, roll).
   *
   * @param eulerAngles an array of Euler angles [pitch, yaw, roll]
   */
  public void setRotation(float[] eulerAngles) {
    this.rotation = eulerAngles != null ? eulerAngles : new float[]{0.0f, 0.0f, 0.0f};  // pitch, yaw, roll
  }

  /**
   * Gets the rotation of the entity as Euler angles (pitch, yaw, roll).
   *
   * @return the rotation as an array of Euler angles [pitch, yaw, roll]
   */
  public float[] getRotation() {
    return rotation;
  }

  // Getters and Setters for position and scale

  public float[] getPosition() {
    return position;
  }

  public void setPosition(float[] position) {
    this.position = position != null ? position : new float[]{0.0f, 0.0f, 0.0f};
  }

  public float[] getScale() {
    return scale;
  }

  public void setScale(float[] scale) {
    this.scale = scale != null ? scale : new float[]{1.0f, 1.0f, 1.0f};
  }
}
