package com.iluwatar.repository;

import com.iluwatar.Person;
import java.util.List;
import java.util.Optional;

/**
 * Provides an interface for managing `Person` objects in a database.
 *
 * @author ASHUdev05
 */
public interface PersonRepository {

  /**
   * Saves a `Person` object to the database.
   *
   * @param person the `Person` object to save
   * @return the saved `Person` object
   */
  Person save(Person person);

  /**
   * Retrieves a `Person` object by its ID.
   *
   * @param id the ID of the `Person` object to retrieve
   * @return the `Person` object with the given ID, or `null` if no such object exists
   */
  Optional<Person> findById(Long id);

  /**
   * Retrieves all `Person` objects from the database.
   *
   * @return a list of all `Person` objects in the database
   */
  List<Person> findAll();

  /**
   * Deletes a `Person` object from the database by its ID.
   *
   * @param id the ID of the `Person` object to delete
   */
  void deleteById(Long id);
}
