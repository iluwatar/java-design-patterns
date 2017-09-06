/**
 * The MIT License Copyright (c) 2016 Amit Dixit
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.datamapper;


import java.io.Serializable;

/**
 * Class defining Student
 */
public final class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  private int studentId;
  private String name;
  private char grade;


  /**
   * Use this constructor to create a Student with all details
   *
   * @param studentId as unique student id
   * @param name as student name
   * @param grade as respective grade of student
   */
  public Student(final int studentId, final String name, final char grade) {
    super();

    this.studentId = studentId;
    this.name = name;
    this.grade = grade;
  }

  /**
   *
   * @return the student id
   */
  public int getStudentId() {
    return studentId;
  }

  /**
   *
   * @param studentId as unique student id
   */
  public void setStudentId(final int studentId) {
    this.studentId = studentId;
  }

  /**
   *
   * @return name of student
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name as 'name' of student
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   *
   * @return grade of student
   */
  public char getGrade() {
    return grade;
  }

  /**
   *
   * @param grade as 'grade of student'
   */
  public void setGrade(final char grade) {
    this.grade = grade;
  }

  /**
 *
 */
  @Override
  public boolean equals(final Object inputObject) {

    boolean isEqual = false;

    /* Check if both objects are same */
    if (this == inputObject) {

      isEqual = true;
    } else if (inputObject != null && getClass() == inputObject.getClass()) {

      final Student inputStudent = (Student) inputObject;

      /* If student id matched */
      if (this.getStudentId() == inputStudent.getStudentId()) {

        isEqual = true;
      }
    }

    return isEqual;
  }

  /**
 *
 */
  @Override
  public int hashCode() {

    /* Student id is assumed to be unique */
    return this.getStudentId();
  }

  /**
 *
 */
  @Override
  public String toString() {
    return "Student [studentId=" + studentId + ", name=" + name + ", grade=" + grade + "]";
  }
}
