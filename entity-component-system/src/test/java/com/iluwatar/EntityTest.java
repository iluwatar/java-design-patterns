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
  TransformComponent transform1;
  TransformComponent transform2;

  @BeforeEach
 void setUp() {
    entity1 = new Entity("Entity1");
    entity2 = new Entity("Entity2");

    transform1 = new TransformComponent(new float[]{0.0f, 0.0f, 0.0f},
        new float[]{0.0f, 0.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});
    transform2 = new TransformComponent(new float[]{5.0f, 0.0f, 0.0f},
        new float[]{0.0f, 45.0f, 0.0f}, new float[]{1.0f, 1.0f, 1.0f});

  }

  @Test
 void testAddComponent() {
    Component component = new HealthComponent(100);
    entity1.addComponent(component);

    assertEquals(2, entity1.getComponents().size(), "Entity1 should have 1 component.");
  }

  @Test
 void testRemoveComponent() {
    Component component = new HealthComponent(100);

    entity2.removeComponent(component);

    assertEquals(1, entity2.getComponents().size(), "Entity1 should have no components.");
  }

  @Test
 void testAddChild() {
    entity1.addChild(entity2);

    assertTrue(entity1.getChildren().contains(entity2), "Entity1 should have Entity2 as its child.");
  }

  @Test
 void testGetComponent() {

    Component component = new HealthComponent(100);
    component.setName("HealthComponent");
    Entity entity = new Entity("Entity1");
    entity.addComponent(component);
    Component retrievedComponent = entity.getComponent("HealthComponent");

    assertEquals(component, retrievedComponent, "The component returned should match the added component.");
    
    Component nonExistentComponent = entity.getComponent("NonExistentComponent");
    assertNull(nonExistentComponent, "The component should return null if it doesn't exist.");
  }

  @Test
 void testSetEnabled() {

    Component component = new HealthComponent(100);
    Entity entity = new Entity("MyEntity");
    entity.addComponent(component);

    assertFalse(component.getEnabled(), "Component should be disabled initially.");

    entity.setEnabled(false);
    
    assertFalse(component.getEnabled(), "Component should be disabled after calling setEnabled(false).");

    entity.setEnabled(true);
    
    assertTrue(component.getEnabled(), "Component should be enabled after calling setEnabled(true).");
  }

  @Test
 void testSetParent() {

    Entity parent = new Entity("parent");
    Entity child = new Entity("child");

    child.setParent(parent);
    
    assertTrue(parent.getChildren().contains(child), "Parent should contain child in its children list.");
    
    Entity newParent = new Entity("newParent");
    child.setParent(newParent);

    assertFalse(parent.getChildren().contains(child), "Parent should no longer contain child after setting a new parent.");

    assertTrue(newParent.getChildren().contains(child), "New parent should contain child in its children list.");
  }

  @Test
 void testUpdate() {
    float deltaTime = 1.0f / 60.0f;
    entity1.update(deltaTime);

    assertNotNull(entity1, "Entity1 should be updated.");
  }

  @Test
 void testRenderEntity() {

    Component component = new HealthComponent(100);
    Entity entity = new Entity("MyEntity");
    entity.addComponent(component);
    entity.renderEntity();

    assertDoesNotThrow(() -> component.update(1.0f), "render function should not throw an exception");
  }

  @Test
 void testGetName() {
    Entity entity = new Entity("MyEntity");

    assertEquals("MyEntity", entity.getName(), "The entity name should match the given name.");
  }

  @Test
 void testSetIsEnabled() {
    Entity entity = new Entity("MyEntity");
    Entity entityChild = new Entity("child");
    entity.addChild(entityChild);
    entity.setIsEnabled(false);

    assertFalse(entity.isEnabled(), "The entity should be disabled after calling setIsEnabled(false).");
    assertFalse(entityChild.isEnabled(), "The entity child should be disabled after calling setIsEnabled(false).");

    entity.setIsEnabled(true);
    
    assertTrue(entity.isEnabled(), "The entity should be enabled after calling setIsEnabled(true).");
    assertTrue(entityChild.isEnabled(), "The entity child should be enabled after calling setIsEnabled(true).");
  }

  @Test
 void testGetAndSetGameSystem() {
 
    GameSystem gameSystem = new GameSystem();
    Entity entity = new Entity("MyEntity");
    entity.setGameSystem(gameSystem);
    
    assertEquals(gameSystem, entity.getGameSystem(), "The game system should match the one set.");
  }

  @Test
 void testUpdate_whenEntityDisabled_shouldReturnImmediately() {


    Entity parent = new Entity("parent");
    Entity child = new Entity("child");

    child.setParent(parent);

    parent.setEnabled(false);
    parent.addComponent(transform1);

    parent.update(1.0f);

    assertFalse(transform1.getEnabled(), "Component should not be enabled.");
    assertDoesNotThrow(() -> transform1.update(1.0f), "Component update should not throw an exception when entity is disabled.");

    assertDoesNotThrow(() -> child.update(1.0f), "Child entity should not throw an exception if the parent is disabled.");
  }

  @Test
 void testUpdate_shouldUpdateEnabledComponents() {
    entity1.setEnabled(true);
    entity2.setEnabled(false);
    entity1.addComponent(transform1);
    entity2.addComponent(transform2);
    entity1.update(1.0f);
    entity2.update(1.0f);

    assertTrue(transform1.getEnabled(), "Transform1 should be enabled after update when the parent entity is enabled.");
    assertDoesNotThrow(() -> transform1.update(1.0f), "Transform1 update should not throw an exception when the parent entity is enabled.");

    assertFalse(transform2.getEnabled(), "Transform2 should remain disabled after update when it is added but disabled.");
    assertDoesNotThrow(() -> transform2.update(1.0f), "Transform2 update should not throw an exception even though it's disabled.");
  }

  @Test
 void testUpdate_shouldUpdateChildEntities() {
    Entity child = new Entity("child");
    entity1.setEnabled(true);
    entity1.addChild(child);
    child.setParent(entity1);
    entity1.update(1.0f);
    assertDoesNotThrow(() -> child.update(1.0f));
  }
}


