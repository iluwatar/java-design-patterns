package com.iluwatar.pac;

/**
 * a class used to present the students' course information.
 */
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentCoursePresentation {
  /**
   * present to the user.
   */
  public static void present(StudentCourseAbstraction abstraction) {
    Object[] objects = abstraction.getNames();

    for (int i = 0; i < objects.length; i++) {
      LOGGER.info(String.format("%s ", (String) objects[i]));

    }
    LOGGER.info("\n");
    for (int i = 0; i < objects.length; i++) {
      LOGGER.info(String.format("%f", abstraction.getGrade((String) objects[i])));

    }
  }

}

