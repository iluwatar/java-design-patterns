package com.iluwatar.pac;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentStatusPresentation {
  /**
   * present the status information to the user.
   *
   * @param pass is the boolean variable that denotes whether the student passed.
   */
  public void present(boolean pass) {

    if (pass) {
      LOGGER.info("The student's average grade satisfied the requirement");
    } else {
      LOGGER.info("The student's average doesn't satisfy the requirement");
    }
  }
}
