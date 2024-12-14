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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentTest {
  @Test
  public void testGetName() {
    Component component = new HealthComponent(100);
    component.setName("Health");

    assertEquals("Health", component.getName(), "getName should return 'TestComponent'");
  }

  @Test
  public void testSetName() {
    Component component = new HealthComponent(100);
    component.setName("Health");

    assertEquals("Health", component.getName(), "getName should return 'TestComponent'");
  }

  @Test
  public void testComponentEnabled() {
    Component component = new HealthComponent(100);
    component.setEnabled(true);

    assertTrue(component.getEnabled(), "The component should be enabled.");

    component.setEnabled(false);
    assertFalse(component.getEnabled(), "The component should be disabled.");
  }

  @Test
  public void testComponentParent() {
    Component component = new HealthComponent(100);
    Entity parentEntity = new Entity("ParentEntity");

    component.setParent(parentEntity);
    assertEquals(parentEntity, component.getParent(), "The component's parent should be set correctly.");
  }
}
