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
import java.time.Period;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which handles actual internal logic and validation for worker registration.
 * Part of the domain layer which collects information and sends it back to the presentation.
 */
@Slf4j
public class RegisterWorker extends ServerCommand {
  static final int LEGAL_AGE = 18;
  protected RegisterWorker(RegisterWorkerDto worker) {
    super(worker);
  }

  /**
   * Validates the data provided and adds it to the database in the backend.
   */
  public void run() {

    validate();
    if (!super.getNotification().hasErrors()) {
      LOGGER.info("Register worker in backend system");
    }
  }

  /**
   * Validates our data. Checks for any errors and if found, adds to notification.
   */
  private void validate() {
    var ourData = ((RegisterWorkerDto) this.data);
    //check if any of submitted data is not given
    // passing for empty value validation
    fail(isNullOrBlank(ourData.getName()), RegisterWorkerDto.MISSING_NAME);
    fail(isNullOrBlank(ourData.getOccupation()), RegisterWorkerDto.MISSING_OCCUPATION);
    fail(isNullOrBlank(ourData.getDateOfBirth()), RegisterWorkerDto.MISSING_DOB);

    if (isNullOrBlank(ourData.getDateOfBirth())) {
      // If DOB is null or empty
      fail(true, RegisterWorkerDto.MISSING_DOB);
    } else {
      // Validating age ( should be greater than or equal to 18 )
      Period age = Period.between(ourData.getDateOfBirth(), LocalDate.now());
      fail(age.getYears() < LEGAL_AGE, RegisterWorkerDto.DOB_TOO_SOON);
    }
  }

  /**
   * Validates for null/empty value.
   *
   * @param obj any object
   * @return boolean
   */
  protected boolean isNullOrBlank(Object obj) {
    if (obj == null) {
      return true;
    }

    if (obj instanceof String) {
      return ((String) obj).trim().isEmpty();
    }

    return false;
  }

  /**
   * If a condition is met, adds the error to our notification.
   *
   * @param condition condition to check for.
   * @param error     error to add if condition met.
   */
  protected void fail(boolean condition, NotificationError error) {
    if (condition) {
      super.getNotification().addError(error);
    }
  }
}
