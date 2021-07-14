package com.iluwatar.rowdatagateway;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * A person POJO that represents the data that will be read from the data
 * source.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Person {
  private int id;
  private String firstName;
  private String lastName;
}