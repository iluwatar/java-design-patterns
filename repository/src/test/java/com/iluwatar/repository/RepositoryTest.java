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
package com.iluwatar.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test case to test the functions of {@link PersonRepository}, beside the CRUD functions, the query
 * by {@link org.springframework.data.jpa.domain.Specification} are also test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"locations=classpath:applicationContext.xml"})
class RepositoryTest {

  @Resource
  private PersonRepository repository;

  private final Person peter = new Person("Peter", "Sagan", 17);
  private final Person nasta = new Person("Nasta", "Kuzminova", 25);
  private final Person john = new Person("John", "lawrence", 35);
  private final Person terry = new Person("Terry", "Law", 36);

  private final List<Person> persons = List.of(peter, nasta, john, terry);

  /**
   * Prepare data for test
   */
  @BeforeEach
  void setup() {
    repository.saveAll(persons);
  }

  @Test
  void testFindAll() {
    var actuals = repository.findAll();
    assertTrue(actuals.containsAll(persons) && persons.containsAll(actuals));
  }

  @Test
  void testSave() {
    var terry = repository.findByName("Terry");
    terry.setSurname("Lee");
    terry.setAge(47);
    repository.save(terry);

    terry = repository.findByName("Terry");
    assertEquals(terry.getSurname(), "Lee");
    assertEquals(47, terry.getAge());
  }

  @Test
  void testDelete() {
    var terry = repository.findByName("Terry");
    repository.delete(terry);

    assertEquals(3, repository.count());
    assertNull(repository.findByName("Terry"));
  }

  @Test
  void testCount() {
    assertEquals(4, repository.count());
  }

  @Test
  void testFindAllByAgeBetweenSpec() {
    var persons = repository.findAll(new PersonSpecifications.AgeBetweenSpec(20, 40));

    assertEquals(3, persons.size());
    assertTrue(persons.stream().allMatch(item -> item.getAge() > 20 && item.getAge() < 40));
  }

  @Test
  void testFindOneByNameEqualSpec() {
    var actual = repository.findOne(new PersonSpecifications.NameEqualSpec("Terry"));
    assertTrue(actual.isPresent());
    assertEquals(terry, actual.get());
  }

  @AfterEach
  void cleanup() {
    repository.deleteAll();
  }

}
