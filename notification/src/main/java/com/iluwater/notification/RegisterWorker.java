package com.iluwater.notification;

import static java.time.LocalDate.now;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which handles actual internal logic and validation for worker registration.
 * Part of the domain layer which collects information and sends it back to the presentation.
 */
@Slf4j
public class RegisterWorker extends ServerCommand {
  protected RegisterWorker(RegisterWorkerDto worker) {
    super(worker);
  }

  /**
   * Validates the data provided and adds it to the database in the backend.
   */
  public void run() {
    //make sure the information submitted is valid
    validate();
    if (!super.getNotification().hasErrors()) {
      //Add worker to system in backend (not implemented here)
      LOGGER.info("Register worker in backend system");
    }
  }

  /**
   * Validates our data. Checks for any errors and if found, stores them in our notification.
   */
  private void validate() {
    RegisterWorkerDto ourData = ((RegisterWorkerDto) this.data);
    //check if any of submitted data is not given
    failIfNullOrBlank(ourData.getName(), RegisterWorkerDto.MISSING_NAME);
    failIfNullOrBlank(ourData.getOccupation(), RegisterWorkerDto.MISSING_OCCUPATION);
    failIfNullOrBlank(ourData.getDateOfBirth().toString(), RegisterWorkerDto.MISSING_DOB);
    //only if DOB is not blank, then check if worker is over 18 to register.
    if (!super.getNotification().getErrors().contains(RegisterWorkerDto.MISSING_DOB)) {
      LocalDate dateOfBirth = ourData.getDateOfBirth();
      LocalDate current = now().minusYears(18);
      fail(dateOfBirth.compareTo(current) > 0, RegisterWorkerDto.DOB_TOO_SOON);
    }
  }

  protected boolean isNullOrBlank(String s) {
    return (s == null || s.equals(""));
  }

  protected void failIfNullOrBlank(String s, NotificationError error) {
    fail(isNullOrBlank(s), error);
  }

  /**
   * If a condition is met, adds the error to our notification.
   *
   * @param condition condition to check for.
   * @param error error to add if condition met.
   */
  protected void fail(boolean condition, NotificationError error) {
    if (condition) {
      super.getNotification().addError(error);
    }
  }
}
