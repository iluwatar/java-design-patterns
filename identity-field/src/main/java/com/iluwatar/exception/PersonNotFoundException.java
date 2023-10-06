package com.iluwatar.exception;

/**
 * An exception that is thrown when a person with the given ID could not be found.
 *
 * @author ASHUdev05
 */
public class PersonNotFoundException extends Exception {

  /**
   * The ID of the person that could not be found.
   */
  private final Long id;

  /**
   * Creates a new `PersonNotFoundException` object.
   *
   * @param id the ID of the person that could not be found
   */
  public PersonNotFoundException(Long id) {
    super("Person with ID " + id + " not found");
    this.id = id;
  }

  /**
   * Gets the ID of the person that could not be found.
   *
   * @return the ID of the person that could not be found
   */
  public Long getId() {
    return id;
  }
}
