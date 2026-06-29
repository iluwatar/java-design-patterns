/*
 *
 *  * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *  *
 *  * The MIT License
 *  * Copyright © 2014-2022 Ilkka Seppälä
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */
package com.iluwatar.onion.infrastructure.persistence;

import com.iluwatar.onion.domain.model.Category;
import com.iluwatar.onion.domain.model.Person;
import com.iluwatar.onion.domain.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepositoryAdapter implements PersonRepository {

  private final SpringDataPersonRepository repository;

  public PersonRepositoryAdapter(SpringDataPersonRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Person> findById(Long id) {
    return repository.findById(id).map(this::mapToDomain);
  }

  @Override
  public Optional<Person> findByFirstName(String firstName) {
    return repository.findByFirstName(firstName).map(this::mapToDomain);
  }

  @Override
  public Optional<Person> findByLastName(String lastName) {
    return repository.findByLastName(lastName).map(this::mapToDomain);
  }

  @Override
  public Optional<List<Person>> findAll() {
    return repository.findAll().stream()
        .map(this::mapToDomain)
        .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of));
  }

  @Override
  public Person save(Person person) {
    JpaPersonEntity entity = mapToEntity(person);
    JpaPersonEntity savedEntity = repository.save(entity);
    return mapToDomain(savedEntity);
  }

  @Override
  public boolean deleteById(Long id) {
    repository.deleteById(id);
    return repository.findById(id).isEmpty();
  }

  private JpaPersonEntity mapToEntity(Person person) {
    return new JpaPersonEntity(
        person.getId(),
        person.getFirstName(),
        person.getLastName(),
        person.getAge(),
        person.getPhoneNumber(),
        person.getEmail(),
        new JpaCategoryEntity(person.getCategory().getId(), person.getCategory().getType()));
  }

  private Person mapToDomain(JpaPersonEntity entity) {
    return new Person(
        entity.getId(),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getAge(),
        entity.getPhoneNumber(),
        entity.getEmail(),
        new Category(entity.getCategory().getId(), entity.getCategory().getType()));
  }
}
