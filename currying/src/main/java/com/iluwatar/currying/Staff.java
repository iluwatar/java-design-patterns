package com.iluwatar.currying;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

/**
 * Staff Object for demonstrating how to use currying pattern.
 */
public class Staff {
  private String firstName;
  private String lastName;
  private Gender gender;
  private String email;
  private LocalDate dateOfBirth;

  /**
   * Contractor.

   * @param firstName first name.
   * @param lastName last name.
   * @param gender gender.
   * @param email email.
   * @param dateOfBirth date of birth.
   */
  public Staff(String firstName,
               String lastName,
               Gender gender,
               String email,
               LocalDate dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Staff staff = (Staff) o;
    return firstName.equals(staff.firstName)
            && lastName.equals(staff.lastName)
            && gender == staff.gender
            && email.equals(staff.email)
            && dateOfBirth.equals(staff.dateOfBirth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, gender, email, dateOfBirth);
  }

  /**
   * Use {@link Function} for currying.
   */
  static Function<String,
            Function<String,
                    Function<Gender,
                            Function<String,
                                    Function<LocalDate, Staff>>>>> CREATOR =
                                        firstName -> lastName
                                            -> gender -> email
                                                -> dateOfBirth
                                                    -> new Staff(firstName, lastName, gender,
                                                         email, dateOfBirth);

  /**
   * Use functional interfaces for currying.
   */
  static AddFirstName builder() {
    return firstName -> lastName
        -> gender -> email
            -> dateOfBirth
                -> new Staff(firstName, lastName, gender, email, dateOfBirth);
  }

  interface AddFirstName {
    AddLastName withReturnFirstName(String firstName);
  }

  interface AddLastName {
    AddGender withReturnLastName(String lastName);
  }

  interface AddGender {
    AddEmail withReturnGender(Gender gender);
  }

  interface AddEmail {
    AddDateOfBirth withReturnEmail(String email);
  }

  interface AddDateOfBirth {
    Staff withReturnDateOfBirth(LocalDate dateOfBirth);
  }

  enum Gender {
    Male, Female
  }

  /**
   * Main method for maven-assembly-plugin.
   */
  public static void main(String[] args) {
    final String firstName = "Janus";
    final String lastName = "Lin";
    final Staff.Gender gender = Staff.Gender.Male;
    final String email = "example@gmail.com";
    final LocalDate dateOfBirth = LocalDate.now();

    Staff staff1 = Staff.CREATOR
            .apply(firstName)
            .apply(lastName)
            .apply(gender)
            .apply(email)
            .apply(dateOfBirth);

    System.out.println(String.format("Staff created with basic currying: %s", staff1));

    Staff staff2 = Staff.builder()
            .withReturnFirstName(firstName)
            .withReturnLastName(lastName)
            .withReturnGender(gender)
            .withReturnEmail(email)
            .withReturnDateOfBirth(dateOfBirth);

    System.out.println(
            String.format("Staff created with currying and functional interfaces: %s", staff2));
  }
}
