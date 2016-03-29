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
import java.util.UUID;

public final class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID guid;
  private int studentID;
  private String name;
  private char grade;

  public Student() {
    this.guid = UUID.randomUUID();
  }

  public Student(final UUID guid, final int studentID, final String name, final char grade) {
    super();

    this.guid = guid;
    this.studentID = studentID;
    this.name = name;
    this.grade = grade;
  }


  public Student(final UUID guid) {
    this.guid = guid;
  }

  public final int getStudentId() {
    return studentID;
  }

  public final void setStudentId(final int studentID) {
    this.studentID = studentID;
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

  public final UUID getGuId() {
    return guid;
  }

  @Override
  public final String toString() {
    return "Student [guid=" + guid + ", studentID=" + studentID + ", name=" + name + ", grade=" + grade + "]";
  }
}
