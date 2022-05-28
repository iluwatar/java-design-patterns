package com.iluwater.pac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AllPublicMethodTest {
  private StudentStatusControl studentStatusControl;

  @BeforeEach
  void start() {
    studentStatusControl = new StudentStatusControl();
  }

  @Test
  void test() {
    studentStatusControl.getStudentCourseControl();
  }

}
