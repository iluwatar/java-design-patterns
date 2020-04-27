/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.associationtable;

public class Student {

  private String name;
  private static int id_count = 1;
  private final int id;

  /**
   * Use this constructor to create a Student object.
   *
   * @param name  as student name
   */
  Student(String name) {
    this.name = name;
    this.id = id_count;
    id_count++;
  }

  void setName(String name) {
    this.name = name;
  }

  String getName() {
    return this.name;
  }

  int getId() {
    return this.id;
  }

  /**
   * Use this function to add a course to the student.
   * If the adding is successful, the association will be added to association table.
   *
   * @param course as the course we want to add
   * @throws RuntimeException if the student is not found in student table
   */
  void addCourse(Course course) throws RuntimeException {
    int j = 0;
    for (int i : StudentTableImpl.students.keySet()) {
      if (i == this.id) {
        j++;
      }
    }
    if (j == 1) {
      if (AssociationTable.associations == null) {
        int[][] result = new int[1][2];
        result[0][0] = this.id;
        result[0][1] = course.getId();
        AssociationTable.associations = result;
      } else {
        AssociationTable.associations =
          this.addElement(AssociationTable.associations, course.getId());
      }
    } else {
      throw new RuntimeException("Student [" + this.name + "] is not found");
    }
  }

  /**
   * Auxiliary function for addCourse(Course course) function.
   *
   * @param arr as the association table
   * @param courseId as the course we want to add
   * @return  new association table
   * @throws RuntimeException if this course is already added
   */
  private int[][] addElement(int[][] arr, int courseId) throws RuntimeException {
    for (int i = 0; i < arr.length; i++) {
      if ((AssociationTable.associations[i][0] == this.id)
          && (AssociationTable.associations[i][1] == courseId)) {
        throw new RuntimeException("Student [" + this.name + "] already took this course");
      }
    }
    int[][] newarr = new int[arr.length + 1][2];
    for (int i = 0; i < arr.length; i++) {
      newarr[i][0] = arr[i][0];
      newarr[i][1] = arr[i][1];
    }
    newarr[arr.length][0] = this.id;
    newarr[arr.length][1] = courseId;
    return newarr;
  }

  /**
   * Use this function to list the courses which belong to this student.
   */
  void listOfCourses() {
    int j = 0;
    for (int i : StudentTableImpl.students.keySet()) {
      if (i == this.id) {
        j++;
      }
    }
    if (j == 1) {
      System.out.println("The list of " + this.name + "'s courses:");
      for (int i = 0; i < AssociationTable.associations.length; i++) {
        if (AssociationTable.associations[i][0] == this.id) {
          System.out.print("\t");
          System.out.println(CourseTableImpl.courses.get(AssociationTable.associations[i][1]));
        }
      }
    } else {
      throw new RuntimeException("Student [" + this.name + "] is not found");
    }
  }

  /**
   * Use this function to delete the connection with a course.
   * If the deleting is successful, the association will be deleted from association table.
   *
   * @param course as the course we want to delete
   * @throws RuntimeException if the student is not found in student table
   *                          or the course doesn't belong to the student
   */
  void deleteCourse(Course course) throws RuntimeException {
    int j = 0;
    for (int i : StudentTableImpl.students.keySet()) {
      if (i == this.id) {
        j++;
      }
    }
    if (j == 1) {
      if ((AssociationTable.associations == null) || (AssociationTable.associations.length == 0)) {
        throw new RuntimeException(this.name + " didn't take this course.");
      }
      for (int i = 0; i < AssociationTable.associations.length; i++) {
        if (AssociationTable.associations[i][0] == this.id
            && AssociationTable.associations[i][1] == course.getId()) {
          AssociationTable.associations =
            this.deleteElement(AssociationTable.associations, course.getId());
          return;
        }
      }
      throw new RuntimeException(this.name + " didn't take this course.");
    } else {
      throw new RuntimeException("Student [" + this.name + "] is not found");
    }
  }

  /**
   * Auxiliary function for deleteCourse(Course course) function.
   *
   * @param arr as the association table
   * @param courseId as the course we want to delete
   * @return  the new association table
   */
  private int[][] deleteElement(int[][] arr, int courseId) {
    int[][] newarr = new int[arr.length - 1][2];
    int i = 0;
    while (!((AssociationTable.associations[i][0] == this.id)
        && (AssociationTable.associations[i][1] == courseId))) {
      newarr[i][0] = arr[i][0];
      newarr[i][1] = arr[i][1];
      i++;
    }
    for (int j = i; j < (arr.length - 1); j++) {
      newarr[j][0] = arr[j + 1][0];
      newarr[j][1] = arr[j + 1][1];
    }
    return newarr;
  }
}
