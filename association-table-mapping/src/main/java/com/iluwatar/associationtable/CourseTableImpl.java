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
 * Implementation of behaviors from CourseTable.
 */
public final class CourseTableImpl implements CourseTable {

  static HashMap<Integer, String> courses = new HashMap<Integer, String>();

  @Override
  public void insert(Course courseToBeInserted) throws RuntimeException {
    int j = 0;
    for (int i : courses.keySet()) {
      if (i == courseToBeInserted.getId()) {
        j++;
      }
    }
    if (j != 1) {
      courses.put(courseToBeInserted.getId(), courseToBeInserted.getName());
    } else {
      throw new RuntimeException("Course [" + courseToBeInserted.getName() + "] already exist");
    }
  }

  @Override
  public void delete(Course courseToBeDeleted) throws RuntimeException {
    int j = 0;
    for (int i : courses.keySet()) {
      if (i == courseToBeDeleted.getId()) {
        j++;
      }
    }
    if (j == 1) {
      courses.remove(courseToBeDeleted.getId());
    } else {
      throw new RuntimeException("Course [" + courseToBeDeleted.getName() + "] is not found");
    }
  }

  /**
   * Use this function to modify a course's name.
   *
   * @param c as the course we want to update
   * @param newName as the course's new name
   * @throws RuntimeException if the course is not found in table
   */
  @Override
  public void update(Course c, String newName) throws RuntimeException {
    for (int i : courses.keySet()) {
      if (i == c.getId()) {
        c.setName(newName);
        courses.replace(i, newName);
        return;
      }
    }
    throw new RuntimeException("Course [" + c.getName() + "] is not found");
  }

  /**
   * The contents of Course table.
   *
   * @return the contents
   */
  public HashMap<Integer, String> getCourses() {
    return courses;
  }

}
