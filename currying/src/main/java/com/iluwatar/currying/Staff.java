package com.iluwatar.currying;

import java.time.LocalDate;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Staff Object for demonstrating how to use currying pattern.
 */
@AllArgsConstructor
@Data
public class Staff {
  private String firstName;
  private String lastName;
  private Gender gender;
  private String email;
  private LocalDate dateOfBirth;

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
