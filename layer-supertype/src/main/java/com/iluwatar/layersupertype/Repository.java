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
package com.iluwatar.layersupertype;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Layer Supertype of the data access layer. It keeps the objects in memory and holds the code that
 * every repository needs: handing out ids, saving, finding and deleting. Concrete repositories add
 * only the queries that are specific to their type.
 *
 * @param <T> the domain type this repository stores
 */
public abstract class Repository<T extends DomainObject> {

  private final Map<Long, T> store = new LinkedHashMap<>();
  private long nextId = 1;

  /**
   * Saves the object, assigning it an id first if it is new. An object that already has an id must
   * have been saved by this repository before, so that ids handed out by another repository can
   * never collide with this one's.
   *
   * @param object the object to save
   * @return the saved object
   * @throws IllegalArgumentException if the object has an id this repository never handed out
   */
  public T save(T object) {
    if (object.isNew()) {
      object.assignId(nextId++);
    } else if (!store.containsKey(object.getId())) {
      throw new IllegalArgumentException("Not managed by this repository: " + object.getId());
    }
    store.put(object.getId(), object);
    return object;
  }

  /**
   * Finds an object by its id.
   *
   * @param id the id to look for
   * @return the object, or empty if none has that id
   */
  public Optional<T> findById(long id) {
    return Optional.ofNullable(store.get(id));
  }

  /**
   * Lists every stored object in the order they were first saved.
   *
   * @return all stored objects
   */
  public List<T> findAll() {
    return new ArrayList<>(store.values());
  }

  /**
   * Removes the object from the store.
   *
   * @param object the object to remove
   * @return true if it was stored
   */
  public boolean delete(T object) {
    return !object.isNew() && store.remove(object.getId()) != null;
  }

  /**
   * Shared helper that concrete repositories build their own queries on.
   *
   * @param condition the condition the objects must meet
   * @return the matching objects
   */
  protected List<T> findWhere(Predicate<T> condition) {
    return store.values().stream().filter(condition).toList();
  }
}
