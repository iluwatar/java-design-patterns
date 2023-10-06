package com.iluwatar;

/**
 * Represents a person.
 *
 * @author ASHUdev05
 */
public class Person {

  /**
   * The identity of the person.
   */
  private long id;

  /**
   * The name of the person.
   */
  private String name;

  /**
   * The age of the person.
   */
  private int age;

  /**
   * Creates a new person object.
   */
  public Person() {
  }

  /**
   * Creates a new person object with the given name and age.
   *
   * @param name the name of the person
   * @param age  the age of the person
   */
  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  /**
   * Gets the identity of the person.
   *
   * @return the identity of the person
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the identity of the person.
   *
   * @param id the identity of the person
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the name of the person.
   *
   * @return the name of the person
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the person.
   *
   * @param name the name of the person
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the age of the person.
   *
   * @return the age of the person
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the age of the person.
   *
   * @param age the age of the person
   */
  public void setAge(int age) {
    this.age = age;
  }
}
