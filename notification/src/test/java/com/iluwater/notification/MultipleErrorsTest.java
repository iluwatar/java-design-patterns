package com.iluwater.notification;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class MultipleErrorsTest {

  @Test
  public void multipleMissingTest() {
    //Define our variables
    String name = null;
    String occupation = "";
    LocalDate dateOfBirth = LocalDate.of(2000, 7, 13);

    //test for errors
    NotificationErrorTest.generalFormSubmissionTest(name, occupation, dateOfBirth,
        Arrays.asList(RegisterWorkerDto.MISSING_NAME, RegisterWorkerDto.MISSING_OCCUPATION),
        Arrays.asList(RegisterWorkerDto.MISSING_DOB, RegisterWorkerDto.DOB_TOO_SOON));
  }
}
