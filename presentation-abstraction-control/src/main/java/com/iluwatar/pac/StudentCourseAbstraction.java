package com.iluwatar.pac;

import java.util.HashMap;
import java.util.Map;

/**
 * a class used to record the data of student grade.
 */
public class StudentCourseAbstraction {
  private Map<String, Float> map = new HashMap<String, Float>();
  /**
   * a method used to get the course names.
   * @return the course name array.
   */

  public Object[] getNames() {
    return map.keySet().toArray();
  }

  /**
   * a method used to get grade.
   *
   * @param name is the course name.
   * @return the grade corresponding to the course name.
   */
  public float getGrade(String name) {
    return map.get(name);
  }

  /**
   * get all the grades.
   *
   * @return the grade array.
   */
  public Object[] getGrades() {
    return map.values().toArray();
  }

  /**
   * add the course with its grade.
   *
   * @param name  is course name.
   * @param grade is the corresponding grade.
   */
  public void addGrade(String name, float grade) {
    map.put(name, grade);
  }

  /**
   * change the grade of the course specified by name.
   *
   * @param name  is course name.
   * @param grade is the corresponding grade.
   * @return the code that denotes whether the operation succeeds.
   */
  public int changeGrade(String name, float grade) {
    if (!map.containsKey(name)) {
      return -1;
    } else {
      map.put(name, grade);
      return 1;
    }
  }
}
