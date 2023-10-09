package com.iluwatar;

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
  RegisterWorkerService service = new RegisterWorkerService();

  /**
   *  Constructor.
   *
   * @param name        Name of the worker
   * @param occupation  occupation of the worker
   * @param dateOfBirth Date of Birth of the worker
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
  public void indicateErrors() {
    worker.getNotification().getErrors().forEach(error -> LOGGER.error(error.toString()));
  }
}
