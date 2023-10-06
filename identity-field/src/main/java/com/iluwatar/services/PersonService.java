package com.iluwatar.services;

import com.iluwatar.Person;
import com.iluwatar.exception.PersonNotFoundException;
import com.iluwatar.repository.PersonRepository;
import java.util.Optional;

/**
 * Provides services for managing `Person` objects.
 *
 * @author ASHUdev05
 */
public class PersonService {

  /**
   * The `PersonRepository` dependency.
   */
  private final PersonRepository personRepository;

  /**
   * Creates a new `PersonService` object.
   *
   * @param personRepository the `PersonRepository` dependency
   */
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  /**
   * Creates a new `Person` object.
   *
   * @param name the name of the person
   * @param age  the age of the person
   * @return the newly created `Person` object
   */
  public Person createPerson(String name, int age) {
    Person person = new Person(name, age);
    personRepository.save(person);
    return person;
  }

  /**
   * Retrieves a `Person` object by its ID.
   *
   * @param id the ID of the person to retrieve
   * @return the `Person` object with the given ID, or `null` if no such object exists
   */
  public Person getPersonById(Long id) throws PersonNotFoundException {
    Optional<Person> personOptional = personRepository.findById(id);
    return personOptional.orElseThrow(() -> new PersonNotFoundException(id));
  }

  /**
   * Updates a `Person` object.
   *
   * @param person the `Person` object to update
   */
  public void updatePerson(Person person) {
    personRepository.save(person);
  }

  /**
   * Deletes a `Person` object by its ID.
   *
   * @param id the ID of the person to delete
   */
  public void deletePersonById(Long id) {
    personRepository.deleteById(id);
  }
}

