package com.iluwater.notification;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 * The form submitted by the user, part of the presentation layer,
 * linked to the domain layer through a data transfer object and
 * linked to the service layer directly.
 */
@Slf4j
public class RegisterWorkerForm {
  String name;
  String occupation;
  LocalDate dateOfBirth;
  RegisterWorkerDto worker;
  /**
   * Service super type which the form uses as part of its service layer.
   */
  RegisterWorkerService service = new RegisterWorkerService();

  /**
   * Creates the form.
   *
   * @param name name of worker
   * @param occupation occupation of the worker
   * @param dateOfBirth date of birth of the worker
   */
  public RegisterWorkerForm(String name, String occupation, LocalDate dateOfBirth) {
    this.name = name;
    this.occupation = occupation;
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * Attempts to submit the form for registering a worker.
   */
  public void submit() {
    //Transmit information to our transfer object to communicate between layers
    saveToWorker();
    //call the service layer to register our worker
    service.registerWorker(worker);

    //check for any errors
    if (worker.getNotification().hasErrors()) {
      indicateErrors();
      LOGGER.info("Not registered, see errors");
    } else {
      LOGGER.info("Registration Succeeded");
    }
  }

  /**
   * Saves worker information to the data transfer object.
   */
  private void saveToWorker() {
    worker = new RegisterWorkerDto();
    worker.setName(name);
    worker.setOccupation(occupation);
    worker.setDateOfBirth(dateOfBirth);
  }

  /**
   * Check for any errors with form submission and show them to the user.
   */
  private void indicateErrors() {
    checkError(RegisterWorkerDto.MISSING_NAME, name);
    checkError(RegisterWorkerDto.MISSING_DOB, dateOfBirth.toString());
    checkError(RegisterWorkerDto.MISSING_OCCUPATION, occupation);
    checkError(RegisterWorkerDto.DOB_TOO_SOON, dateOfBirth.toString());
  }

  /**
   * Checks if an error is present and display it to the user if so.
   *
   * @param error the error type to check for
   * @param info the message to print if an error if found
   */
  private void checkError(NotificationError error, String info) {
    if (worker.getNotification().getErrors().contains(error)) {
      showError(info, error.toString());
    }
  }

  /**
   * Displays an error message.
   *
   * @param info information to do with the error
   * @param message message to display
   */
  void showError(String info, String message) {
    LOGGER.info(message + ": \"" + info + "\"");
  }
}
