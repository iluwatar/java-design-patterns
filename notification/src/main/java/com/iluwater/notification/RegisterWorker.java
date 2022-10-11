package com.iluwater.notification;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Slf4j
public class RegisterWorker extends ServerCommand {
  protected RegisterWorker(RegisterWorkerDTO worker) {
    super(worker);
  }

  public void run() {
    validate();
    if (!super.getNotification().hasErrors()) {
        //Add worker to system in backend (not implemented here)
        LOGGER.info("Register worker in backend system");
    }
  }

  private void validate() {
    RegisterWorkerDTO ourData = ((RegisterWorkerDTO) this.data);
    failIfNullOrBlank(ourData.getName(), RegisterWorkerDTO.MISSING_NAME);
    failIfNullOrBlank(ourData.getOccupation(), RegisterWorkerDTO.MISSING_OCCUPATION);
    failIfNullOrBlank(ourData.getDateOfBirth().toString(), RegisterWorkerDTO.MISSING_DOB);
    if (!super.getNotification().getErrors().contains(RegisterWorkerDTO.MISSING_DOB)) {
        LocalDate DOB = ourData.getDateOfBirth();
        LocalDate current = now().minusYears(18);
        fail(DOB.compareTo(current) > 0, RegisterWorkerDTO.DOB_TOO_SOON);
    }
  }

  protected boolean isNullOrBlank(String s) {
    return (s == null || s.equals(""));
  }

  protected void failIfNullOrBlank (String s, NotificationError error) {
    fail (isNullOrBlank(s), error);
  }

  protected void fail(boolean condition, NotificationError error) {
    if (condition) {
        super.getNotification().addError(error);
    }
  }
}
