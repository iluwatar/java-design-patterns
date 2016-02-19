package com.iluwatar.monad;

public class User {

  private String name;
  private int age;
  private Sex sex;
  private String email;

  /**
   * @param name  - name
   * @param age   - age
   * @param sex   - sex
   * @param email - email address
   */
  public User(String name, int age, Sex sex, String email) {
    this.name = name;
    this.age = age;
    this.sex = sex;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Sex getSex() {
    return sex;
  }

  public String getEmail() {
    return email;
  }
}
