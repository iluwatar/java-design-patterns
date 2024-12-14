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

public class VelocityComponentTest {

  private VelocityComponent velocity;

  @BeforeEach
  public void setUp() {
    velocity = new VelocityComponent(1.0f, 0.0f, 0.0f);
  }

  @Test
  public void testApplyForce() {
    velocity.applyForce(2.0f, 0.0f, 0.0f);

    assertEquals(3.0f, velocity.getVelocityX(), "VelocityX should increase after applying force.");
  }

  @Test
  public void testApplyFriction() {
    velocity.applyFriction(0.1f);  // Applying friction coefficient of 0.1

    assertEquals(0.9f, velocity.getVelocityX(), "VelocityX should be reduced by the friction coefficient.");
  }

  @Test
  public void testReset_shouldResetVelocityToZero() {

    velocity.setVelocityX( 5.0f);
    velocity.setVelocityY( 10.0f);
    velocity.setVelocityZ( 15.0f);
    velocity.resetVelocity();

    assertEquals(1.0f, velocity.getVelocityX(), "Velocity X should be reset to 1.0f");
    assertEquals(0.0f, velocity.getVelocityY(), "Velocity Y should be reset to 0");
    assertEquals(0.0f, velocity.getVelocityZ(), "Velocity Z should be reset to 0");
  }

  @Test
  public void testUpdateVelocity() {
    float deltaTime = 1.0f / 60.0f;

    float initialVelocityX = velocity.getVelocityX();
    velocity.update(deltaTime);

    assertTrue(velocity.getVelocityX() > initialVelocityX, "VelocityX should increase after updating.");
  }

  @Test
  public void testSetVelocityX() {
    
    velocity = new VelocityComponent(1.0f, 0.0f, 0.0f);
    velocity.setVelocityX(5.0f);
    assertEquals(5.0f, velocity.getVelocityX(), "The velocityX should be set to 5.0f");
    
    velocity.setVelocityX(10.0f);
    assertEquals(10.0f, velocity.getVelocityX(), "The velocityX should now be set to 10.0f");
  }
  @Test
  public void testSetVelocityZ() {

    velocity = new VelocityComponent(1.0f, 0.0f, 0.0f);
    velocity.setVelocityZ(5.0f);
    assertEquals(5.0f, velocity.getVelocityZ(), "The velocityZ should be set to 5.0f");

    velocity.setVelocityZ(10.0f);
    assertEquals(10.0f, velocity.getVelocityZ(), "The velocityZ should now be set to 10.0f");
  }
}
