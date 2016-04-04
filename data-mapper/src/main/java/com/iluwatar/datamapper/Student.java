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

public final class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  private int studentId;
  private String name;
  private char grade;

  public Student() {

  }

  public Student(final int studentId, final String name, final char grade) {
    super();

    this.studentId = studentId;
    this.name = name;
    this.grade = grade;
  }

  public final int getStudentId() {
    return studentId;
  }

  public final void setStudentId(final int studentId) {
    this.studentId = studentId;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  public final char getGrade() {
    return grade;
  }

  public final void setGrade(final char grade) {
    this.grade = grade;
  }

  @Override
  public boolean equals(final Object inputObject) {

    boolean isEqual = false;

    /* Check if both objects are same */
    if (this == inputObject) {

      isEqual = true;
    }
    /* Check if objects belong to same class */
    else if (inputObject != null && getClass() == inputObject.getClass()) {

      final Student student = (Student) inputObject;

      /* If student id matched */
      if (this.getStudentId() == student.getStudentId()) {

        isEqual = true;
      }
    }
    return isEqual;
  }

  @Override
  public int hashCode() {

    /* Student id is assumed to be unique */
    return this.getStudentId();
  }

  @Override
  public final String toString() {
    return "Student [studentId=" + studentId + ", name=" + name + ", grade=" + grade + "]";
  }
}
