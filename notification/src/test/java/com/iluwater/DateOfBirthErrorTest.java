package com.iluwater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DateOfBirthErrorTest {
  @Test
  public void dateOfBirthTest() {
    //Define our variables
    String name = "valid name";
    String occupation = "valid occupation";
    LocalDate dateOfBirth = LocalDate.of(2016, 7, 13);

    //test for errors
    NotificationErrorTest.generalFormSubmissionTest(name, occupation, dateOfBirth,
        List.of(RegisterWorkerDto.DOB_TOO_SOON),
        Arrays.asList(RegisterWorkerDto.MISSING_DOB, RegisterWorkerDto.MISSING_NAME, RegisterWorkerDto.MISSING_OCCUPATION));
  }
}
