package com.iluwater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OneErrorTest {
  @Test
  public void missingNameTest() {
    //Define our variables
    String name = "";
    String occupation = "Valid Occupation";
    LocalDate dateOfBirth = LocalDate.of(2000, 7, 13);

    //test for no errors
    NotificationErrorTest.generalFormSubmissionTest(name, occupation, dateOfBirth, List.of(RegisterWorkerDto.MISSING_NAME),
        Arrays.asList(RegisterWorkerDto.MISSING_DOB, RegisterWorkerDto.DOB_TOO_SOON, RegisterWorkerDto.MISSING_OCCUPATION));
  }
}
