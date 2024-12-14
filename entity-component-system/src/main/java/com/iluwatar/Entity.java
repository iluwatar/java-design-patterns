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
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an entity in the ECS system.
 * Each entity can have components, children entities, and a parent entity.
 * Entities can be enabled or disabled, and their components can be updated.
 */
public class Entity {

  private String name;
  private boolean isEnabled;
  private TransformComponent transform;
  private Entity parent;
  private GameSystem gameSystem;
  private List<Component> components;
  private List<Entity> children;

  /** The unique identifier for the entity. */
  private UUID entityId;

  /**
   * Constructs a new entity with a specified name.
   *
   * @param entityName the name of the entity
   */
  public Entity(String entityName) {
    name = entityName;
    components = new ArrayList<>();
    children = new ArrayList<>();
    final UUID entityId = UUID.randomUUID(); // Generates a unique UUID for this entity
    transform = new TransformComponent(new float[] {0.0f, 0.0f, 0.0f}, new float[] {0.0f, 0.0f, 0.0f}, new float[] {1.0f, 1.0f, 1.0f});
    addComponent(transform);

  }

  /**
   * Adds a component to the entity.
   *
   * @param component the component to be added
   */
  public void addComponent(Component component) {
    if (component != null) {
      components.add(component);
      component.setEnabled(isEnabled);
      component.setParent(this);
    }
  }

  /**
   * Removes a component from the entity.
   *
   * @param component the component to be removed
   */
  public void removeComponent(Component component) {
    components.remove(component);
  }

  /**
   * Retrieves a component by its name.
   *
   * @param componentName the name of the component
   * @return the component matching the specified name, or null if not found
   */
  public Component getComponent(String componentName) {
    for (Component component : components) {
      if (Objects.equals(component.getName(), componentName)) {
        return component;
      }
    }
    return null;
  }

  /**
   * Sets the parent entity for this entity.
   *
   * @param newParent the new parent entity
   */
  public void setParent(Entity newParent) {
    if (parent != null) {
      parent.removeChild(this);
    }

    parent = newParent;

    if (parent != null) {
      parent.addChild(this);
    }
  }

  /**
   * Adds a child entity to this entity.
   *
   * @param child the child entity to be added
   */
  public void addChild(Entity child) {
    if (child != null && !children.contains(child)) {
      children.add(child);
      // Set the parent only if it isn't already set.
      if (child.getParent() != this) {
        child.setParent(this);
      }
    }
  }


  /**
   * Removes a child entity from this entity.
   *
   * @param child the child entity to be removed
   */
  public void removeChild(Entity child) {
    children.remove(child);
  }

  /**
   * Enables or disables the entity and all its components and children.
   *
   * @param enabled whether the entity should be enabled or not
   */
  public void setEnabled(boolean enabled) {
    isEnabled = enabled;

    for (Component component : components) {
      component.setEnabled(enabled);
    }

    for (Entity child : children) {
      child.setEnabled(enabled);
    }
  }

  public boolean getEnabled() {
    return isEnabled;
  }

  /**
   * Updates the entity and its components.
   *
   * @param deltaTime the time elapsed since the last update
   */
  public void update(float deltaTime) {
    if (!isEnabled) {
      return;
    }

    for (Component component : components) {
      if (component.getEnabled()) {
        component.update(deltaTime);
      }
    }

    for (Entity child : children) {
      child.update(deltaTime);
    }
  }

  /**
   * Gets the transform component of the entity.
   *
   * @return the transform component
   */
  public TransformComponent getTransformComponent() {
    return transform;
  }

  /**
   * Gets the transform component of the entity.
   *
   */
  public void setTransformComponent(TransformComponent transform) {
    this.transform = transform;
  }

  /**
   * Retrieves the mesh render components associated with this entity.
   *
   * @return a list of mesh render components
   */
  public List<Component> getComponents() {

    return components;
  }

  /**
   * Renders the entity and its components.
   */
  public void renderEntity() {
    for (Component component : components) {
      // Render each component
    }
  }

  // Getters and Setters for fields

  /**
   * Gets the name of the entity.
   *
   * @return the name of the entity
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the unique identifier of the entity.
   *
   * @return the unique entity identifier
   */
  public UUID getEntityId() {
    return entityId;
  }

  /**
   * Gets the current enabled state of the entity.
   *
   * @return true if the entity is enabled, false otherwise
   */
  public boolean isEnabled() {
    return isEnabled;
  }

  /**
   * Sets the current enabled state of the entity.
   *
   * @param isEnabled true to enable the entity, false to disable it
   */
  public void setIsEnabled(boolean isEnabled) {

    this.isEnabled = isEnabled;

    for (Entity child : children) {
      child.setIsEnabled(isEnabled);

    }
  }

  /**
   * Gets the game system this entity belongs to.
   *
   * @return the game system the entity is part of
   */
  public GameSystem getGameSystem() {
    return gameSystem;
  }

  /**
   * Sets the game system this entity belongs to.
   *
   * @param gameSystem the game system to set
   */
  public void setGameSystem(GameSystem gameSystem) {
    this.gameSystem = gameSystem;
  }

  /**
   * Gets the parent entity of this entity.
   *
   * @return the parent entity
   */
  public Entity getParent() {
    return parent;
  }

  /**
   * Gets the children entities of this entity.
   *
   * @return the list of children entities
   */
  public List<Entity> getChildren() {
    return children;
  }
}
