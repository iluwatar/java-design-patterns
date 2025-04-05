/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 * The form submitted by the user, part of the presentation layer, linked to the domain layer
 * through a data transfer object and linked to the service layer directly.
 */
@Slf4j
public class RegisterWorkerForm {
  String name;
  String occupation;
  LocalDate dateOfBirth;
  RegisterWorkerDto worker;
  RegisterWorkerService service = new RegisterWorkerService();

  /**
   * Constructor.
   *
   * @param name Name of the worker
   * @param occupation occupation of the worker
   * @param dateOfBirth Date of Birth of the worker
   */
  public RegisterWorkerForm(String name, String occupation, LocalDate dateOfBirth) {
    this.name = name;
    this.occupation = occupation;
    this.dateOfBirth = dateOfBirth;
  }

  /** Attempts to submit the form for registering a worker. */
  public void submit() {
    // Transmit information to our transfer object to communicate between layers
    saveToWorker();
    // call the service layer to register our worker
    service.registerWorker(worker);

    // check for any errors
    if (worker.getNotification().hasErrors()) {
      indicateErrors();
      LOGGER.info("Not registered, see errors");
    } else {
      LOGGER.info("Registration Succeeded");
    }
  }

  /** Saves worker information to the data transfer object. */
  private void saveToWorker() {
    worker = new RegisterWorkerDto();
    worker.setName(name);
    worker.setOccupation(occupation);
    worker.setDateOfBirth(dateOfBirth);
  }

  /** Check for any errors with form submission and show them to the user. */
  public void indicateErrors() {
    worker.getNotification().getErrors().forEach(error -> LOGGER.error(error.toString()));
  }
}
