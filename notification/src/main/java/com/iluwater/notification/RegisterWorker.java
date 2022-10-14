package com.iluwater.notification;

import static java.time.LocalDate.now;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class RegisterWorker extends ServerCommand {
  protected RegisterWorker(RegisterWorkerDto worker) {
    super(worker);
  }

  /**
   *
   */
  public void run() {
    validate();
    if (!super.getNotification().hasErrors()) {
      //Add worker to system in backend (not implemented here)
      LOGGER.info("Register worker in backend system");
    }
  }

  private void validate() {
    RegisterWorkerDto ourData = ((RegisterWorkerDto) this.data);
    failIfNullOrBlank(ourData.getName(), RegisterWorkerDto.MISSING_NAME);
    failIfNullOrBlank(ourData.getOccupation(), RegisterWorkerDto.MISSING_OCCUPATION);
    failIfNullOrBlank(ourData.getDateOfBirth().toString(), RegisterWorkerDto.MISSING_DOB);
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

  protected void fail(boolean condition, NotificationError error) {
    if (condition) {
      super.getNotification().addError(error);
    }
  }
}
