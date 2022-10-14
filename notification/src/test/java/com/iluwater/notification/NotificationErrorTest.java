package com.iluwater.notification;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationErrorTest {
  public static void generalFormSubmissionTest(String name, String occupation, LocalDate dateOfBirth,
                                               List<NotificationError> containsError, List<NotificationError> excludesError) {
    //Assign the variables to a new DTO, equivalent to filling in the form
    RegisterWorkerDto dto = new RegisterWorkerDto();
    dto.setupWorkerDto(name, occupation, dateOfBirth);

    //Run the registration process, equivalent to submitting the form
    RegisterWorker register = new RegisterWorker(dto);
    register.run();

    //check for correct errors contained
    boolean containsErrorBool = true;
    boolean excludesErrorBool = true;
    List<NotificationError> errorList = dto.getNotification().getErrors();
    if (containsError != null) {
      containsErrorBool = errorList.containsAll(containsError);
    }
    if (excludesError != null) { //Check if correct error is NOT contained
      for (NotificationError error : errorList) {
        if (excludesError.contains(error)) {
          excludesErrorBool = false;
          break;
        }
      }
    }
    assertTrue(containsErrorBool);
    assertTrue(excludesErrorBool);
  }
}
