package com.iluwatar.pac;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentAverageGradePresentation {

  /**
   * present average grade to user.
   *
   * @param avg is the average grade.
   */
  public void present(double avg) {
    String s = String.format("The average grade is %f\n", avg);
    LOGGER.info(s);
  }

}
