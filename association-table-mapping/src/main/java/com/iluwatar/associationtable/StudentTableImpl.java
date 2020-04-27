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

import java.util.HashMap;

/**
 * Implementation of behaviors from StudentTable.
 */
public final class StudentTableImpl implements StudentTable {

  static HashMap<Integer, String> students = new HashMap<Integer, String>();

  @Override
  public void insert(Student studentToBeInserted) throws RuntimeException {
    int j = 0;
    for (int i : students.keySet()) {
      if (i == studentToBeInserted.getId()) {
        j++;
      }
    }
    if (j != 1) {
      students.put(studentToBeInserted.getId(), studentToBeInserted.getName());
    } else {
      throw new RuntimeException("Student [" + studentToBeInserted.getName() + "] already exist");
    }
  }

  @Override
  public void delete(Student studentToBeDeleted) throws RuntimeException {
    int j = 0;
    for (int i : students.keySet()) {
      if (i == studentToBeDeleted.getId()) {
        j++;
      }
    }
    if (j == 1) {
      students.remove(studentToBeDeleted.getId());
    } else {
      throw new RuntimeException("Student [" + studentToBeDeleted.getName() + "] is not found");
    }
  }

  /**
   * Use this function to modify a student's name.
   *
   * @param st as the student we want to update
   * @param newName as the student's new name
   * @throws RuntimeException if the student is not found in table
   */
  @Override
  public void update(Student st, String newName) throws RuntimeException {
    for (int i : students.keySet()) {
      if (i == st.getId()) {
        st.setName(newName);
        students.replace(i, newName);
        return;
      }
    }
    throw new RuntimeException("Student [" + st.getName() + "] is not found");
  }

  /**
   * The contents of Student table.
   *
   * @return the contents
   */
  public HashMap<Integer, String> getStudents() {
    return students;
  }

}
