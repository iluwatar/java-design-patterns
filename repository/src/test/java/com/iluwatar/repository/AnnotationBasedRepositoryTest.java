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

package com.iluwatar.repository;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case to test the functions of {@link PersonRepository}, beside the CRUD functions, the query
 * by {@link org.springframework.data.jpa.domain.Specification} are also test.
 * 
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { AppConfig.class })
public class AnnotationBasedRepositoryTest {

  @Resource
  private PersonRepository repository;

  Person peter = new Person("Peter", "Sagan", 17);
  Person nasta = new Person("Nasta", "Kuzminova", 25);
  Person john = new Person("John", "lawrence", 35);
  Person terry = new Person("Terry", "Law", 36);

  List<Person> persons = List.of(peter, nasta, john, terry);

  /**
   * Prepare data for test
   */
  @BeforeEach
  public void setup() {

    repository.saveAll(persons);
  }

  @Test
  public void testFindAll() {

    List<Person> actuals = Lists.newArrayList(repository.findAll());
    assertTrue(actuals.containsAll(persons) && persons.containsAll(actuals));
  }

  @Test
  public void testSave() {

    Person terry = repository.findByName("Terry");
    terry.setSurname("Lee");
    terry.setAge(47);
    repository.save(terry);

    terry = repository.findByName("Terry");
    assertEquals(terry.getSurname(), "Lee");
    assertEquals(47, terry.getAge());
  }

  @Test
  public void testDelete() {

    Person terry = repository.findByName("Terry");
    repository.delete(terry);

    assertEquals(3, repository.count());
    assertNull(repository.findByName("Terry"));
  }

  @Test
  public void testCount() {

    assertEquals(4, repository.count());
  }

  @Test
  public void testFindAllByAgeBetweenSpec() {

    List<Person> persons = repository.findAll(new PersonSpecifications.AgeBetweenSpec(20, 40));

    assertEquals(3, persons.size());
    assertTrue(persons.stream().allMatch((item) -> {
      return item.getAge() > 20 && item.getAge() < 40;
    }));
  }

  @Test
  public void testFindOneByNameEqualSpec() {

    Optional<Person> actual = repository.findOne(new PersonSpecifications.NameEqualSpec("Terry"));
    assertEquals(terry, actual.get());
  }

  @AfterEach
  public void cleanup() {

    repository.deleteAll();
  }

}
