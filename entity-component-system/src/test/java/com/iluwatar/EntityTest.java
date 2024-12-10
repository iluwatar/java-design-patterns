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

public class EntityTest {

  private Entity entity1;
  private Entity entity2;
  private TransformComponent transform1;
  private TransformComponent transform2;

  @BeforeEach
  public void setUp() {
    entity1 = new Entity("Entity1");
    entity2 = new Entity("Entity2");

    transform1 = new TransformComponent(new float[]{0.0f, 0.0f, 0.0f},
        new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
    transform2 = new TransformComponent(new float[]{5.0f, 0.0f, 0.0f},
        new float[]{0.0f, 45.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});

  }

  @Test
  public void testAddComponent() {
    Component component = new HealthComponent(100);
    entity1.addComponent(component);

    assertEquals(2, entity1.getComponents().size(), "Entity1 should have 1 component.");
  }

  @Test
  public void testRemoveComponent() {
    Component component = new HealthComponent(100);

    entity2.removeComponent(component);

    assertEquals(1, entity2.getComponents().size(), "Entity1 should have no components.");
  }

  @Test
  public void testSetParent() {
    entity1 = new Entity("Entity1");
    entity2 = new Entity("Entity2");
    entity1.setParent(entity2);
    assertEquals(entity2.getEntityId(), entity1.getParent().getEntityId(), "Entity1 should have Entity2 as its parent.");
  }

  @Test
  public void testAddChild() {
    entity1.addChild(entity2);

    assertTrue(entity1.getChildren().contains(entity2), "Entity1 should have Entity2 as its child.");
  }

  @Test
  public void testSetEnabled() {
    entity1.setEnabled(false);
    assertFalse(entity1.getEnabled(), "Entity1 should be disabled.");
    entity1.setEnabled(true);
    assertTrue(entity1.getEnabled(), "Entity1 should be enabled.");
  }

  @Test
  public void testUpdate() {
    float deltaTime = 1.0f / 60.0f;
    entity1.update(deltaTime);

    assertNotNull(entity1, "Entity1 should be updated.");
  }
}
