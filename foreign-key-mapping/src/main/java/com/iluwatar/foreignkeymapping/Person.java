package com.iluwatar.foreignkeymapping;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Person definition.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private int personNationalId;
  private String lastName;
  private String firstName;
  private long age;

  @Override
  public String toString() {

    return "Person ID is : " + personNationalId + " ; Person Last Name is : " + lastName + " ; Person First Name is : " + firstName + " ; Age is :" + age;

  }

}