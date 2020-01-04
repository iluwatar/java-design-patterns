/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.servicelayer.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.iluwatar.servicelayer.hibernate.HibernateUtil;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Date: 12/28/15 - 10:53 PM Test for Base Data Access Objects
 *
 * @param <E> Type of Base Entity
 * @param <D> Type of Dao Base Implementation
 * @author Jeroen Meulemeester
 */
public abstract class BaseDaoTest<E extends BaseEntity, D extends DaoBaseImpl<E>> {

  /**
   * The number of entities stored before each test
   */
  private static final int INITIAL_COUNT = 5;

  /**
   * The unique id generator, shared between all entities
   */
  private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

  /**
   * Factory, used to create new entity instances with the given name
   */
  private final Function<String, E> factory;

  /**
   * The tested data access object
   */
  private final D dao;

  /**
   * Create a new test using the given factory and dao
   *
   * @param factory The factory, used to create new entity instances with the given name
   * @param dao     The tested data access object
   */
  public BaseDaoTest(final Function<String, E> factory, final D dao) {
    this.factory = factory;
    this.dao = dao;
  }

  @BeforeEach
  public void setUp() {
    for (int i = 0; i < INITIAL_COUNT; i++) {
      final var className = dao.persistentClass.getSimpleName();
      final var entityName = String.format("%s%d", className, ID_GENERATOR.incrementAndGet());
      this.dao.persist(this.factory.apply(entityName));
    }
  }

  @AfterEach
  public void tearDown() {
    HibernateUtil.dropSession();
  }

  protected final D getDao() {
    return this.dao;
  }

  @Test
  public void testFind() {
    final var all = this.dao.findAll();
    for (final var entity : all) {
      final var byId = this.dao.find(entity.getId());
      assertNotNull(byId);
      assertEquals(byId.getId(), byId.getId());
    }
  }

  @Test
  public void testDelete() {
    final var originalEntities = this.dao.findAll();
    this.dao.delete(originalEntities.get(1));
    this.dao.delete(originalEntities.get(2));

    final var entitiesLeft = this.dao.findAll();
    assertNotNull(entitiesLeft);
    assertEquals(INITIAL_COUNT - 2, entitiesLeft.size());
  }

  @Test
  public void testFindAll() {
    final var all = this.dao.findAll();
    assertNotNull(all);
    assertEquals(INITIAL_COUNT, all.size());
  }

  @Test
  public void testSetId() {
    final var entity = this.factory.apply("name");
    assertNull(entity.getId());

    final var expectedId = 1L;
    entity.setId(expectedId);
    assertEquals(expectedId, entity.getId());
  }

  @Test
  public void testSetName() {
    final var entity = this.factory.apply("name");
    assertEquals("name", entity.getName());
    assertEquals("name", entity.toString());

    final var expectedName = "new name";
    entity.setName(expectedName);
    assertEquals(expectedName, entity.getName());
    assertEquals(expectedName, entity.toString());
  }

}
