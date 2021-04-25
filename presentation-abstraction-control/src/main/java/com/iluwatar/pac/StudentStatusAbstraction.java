package com.iluwatar.pac;

public class StudentStatusAbstraction {
  /**
   * this pass boolean type variable denotes whether the student's average grade is larger than 60.
   */
  private boolean pass = false;

  public boolean getPass() {
    return pass;
  }

  /**
   * set the pass according to average grade.
   *
   * @param avg is the average grade.
   */
  public void setPass(float avg) {
    pass = avg >= 60;
  }


}
