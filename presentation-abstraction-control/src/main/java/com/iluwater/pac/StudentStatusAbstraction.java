package com.iluwater.pac;

import lombok.Getter;

/**
 * a class used to abstract student status.
 */

public class StudentStatusAbstraction {
  /**
   * this pass boolean type variable denotes whether the student's average grade is larger than 60.
   */
  @Getter
  private boolean pass = false;

  /**
   * set the pass according to average grade.
   *
   * @param avg is the average grade.
   */
  public void setPass(float avg) {
    pass = avg >= 60;
  }


}
