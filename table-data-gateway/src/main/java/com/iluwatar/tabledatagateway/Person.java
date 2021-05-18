package com.iluwatar.tabledatagateway;

/**
 * The type Person.
 */
public class Person implements PersonInterface{

  private int id;
  private String firstName;
  private String lastName;
  private String gender;
  private int age;

  /**
   * Instantiates a new Person.
   *
   * @param id        the id
   * @param firstName the first name
   * @param lastName  the last name
   * @param gender    the gender
   * @param age       the age
   */
  Person(int id, String firstName, String lastName, String gender, int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.age = age;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  @Override
  public int getId() {
    return id;
  }
  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  @Override
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  @Override
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets gender.
   *
   * @return the gender
   */
  @Override
  public String getGender() {
    return gender;
  }

  /**
   * Sets gender.
   *
   * @param gender the gender
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Gets age.
   *
   * @return the age
   */
  @Override
  public int getAge() {
    return age;
  }

  /**
   * Sets age.
   *
   * @param age the age
   */
  public void setAge(int age) {
    this.age = age;
  }


}
