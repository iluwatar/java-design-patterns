package com.iluwatar.effectivity;

import java.util.ArrayList;

/**
 * A person represents a person with a name, and a list of {@link Employment}s.
 */
class Person extends NamedObject {
  private final ArrayList<Employment> employments;

  public Person(String name) {
    super(name);
    employments =  new ArrayList<>();
  }

  Employment[] employments() {
    return employments.toArray(new Employment[0]);
  }

  void addEmployment(Company company, SimpleDate startDate) {
    employments.add(new Employment(company, startDate));
  }

  public void addEmployment(Employment employment) {
    employments.add(employment);
  }
}
