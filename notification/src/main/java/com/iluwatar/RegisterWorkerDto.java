package com.iluwatar;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object which stores information about the worker. This is carried between
 * objects and layers to reduce the number of method calls made.
 */
@Getter
@Setter
public class RegisterWorkerDto extends DataTransferObject {
  private String name;
  private String occupation;
  private LocalDate dateOfBirth;

  /**
   * Error for when name field is blank or missing.
   */
  public static final NotificationError MISSING_NAME =
          new NotificationError(1, "Name is missing");

  /**
   * Error for when occupation field is blank or missing.
   */
  public static final NotificationError MISSING_OCCUPATION =
          new NotificationError(2, "Occupation is missing");

  /**
   * Error for when date of birth field is blank or missing.
   */
  public static final NotificationError MISSING_DOB =
          new NotificationError(3, "Date of birth is missing");

  /**
   * Error for when date of birth is less than 18 years ago.
   */
  public static final NotificationError DOB_TOO_SOON =
          new NotificationError(4, "Worker registered must be over 18");


  protected RegisterWorkerDto() {
    super();
  }

  /**
   * Simple set up function for capturing our worker information.
   *
   * @param name        Name of the worker
   * @param occupation  occupation of the worker
   * @param dateOfBirth Date of Birth of the worker
   */
  public void setupWorkerDto(String name, String occupation, LocalDate dateOfBirth) {
    this.name = name;
    this.occupation = occupation;
    this.dateOfBirth = dateOfBirth;
  }
}
