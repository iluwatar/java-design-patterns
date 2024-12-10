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

public class TransformComponentTest {

  private TransformComponent transform;

  @BeforeEach
  public void setUp() {
    transform = new TransformComponent(new float[]{0.0f, 0.0f, 0.0f},
        new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
  }

  @Test
  public void testGetPosition() {
    float[] position = transform.getPosition();

    assertArrayEquals(new float[]{0.0f, 0.0f, 0.0f}, position, "The position should be initialized correctly.");
  }

  @Test
  public void testSetPosition() {
    transform.setPosition(new float[]{10.0f, 20.0f, 30.0f});

    assertArrayEquals(new float[]{10.0f, 20.0f, 30.0f}, transform.getPosition(), "Position should be updated correctly.");
  }

  @Test
  public void testGetRotation() {
    float[] rotation = transform.getRotation();

    assertArrayEquals(new float[]{0.0f, 0.0f, 0.0f}, rotation, "The rotation should be initialized correctly.");
  }

  @Test
  public void testSetRotation() {
    transform.setRotation(new float[]{90.0f, 0.0f, 0.0f});

    assertArrayEquals(new float[]{90.0f, 0.0f, 0.0f}, transform.getRotation(), "Rotation should be updated correctly.");
  }

  @Test
  public void testGetScale() {
    float[] scale = transform.getScale();

    assertArrayEquals(new float[]{1.0f, 1.0f, 1.0f}, scale, "The scale should be initialized correctly.");
  }

  @Test
  public void testSetScale() {
    transform.setScale(new float[]{2.0f, 2.0f, 2.0f});

    assertArrayEquals(new float[]{2.0f, 2.0f, 2.0f}, transform.getScale(), "Scale should be updated correctly.");
  }
}
