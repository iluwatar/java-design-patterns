package com.iluwater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class NoErrorsTest {
  @Test
  public void validSubmissionTest() {
    //Define our variables
    String name = "Valid Name";
    String occupation = "Valid Occupation";
    LocalDate dateOfBirth = LocalDate.of(2000, 7, 13);

    //test for no errors
    NotificationErrorTest.generalFormSubmissionTest(name, occupation, dateOfBirth, null,
        Arrays.asList(RegisterWorkerDto.MISSING_DOB, RegisterWorkerDto.DOB_TOO_SOON,
            RegisterWorkerDto.MISSING_NAME, RegisterWorkerDto.MISSING_OCCUPATION));
  }
}
