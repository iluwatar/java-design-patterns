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

import java.util.List;
import java.util.Optional;

public final class StudentMySQLDataMapper implements StudentDataMapper {

  /* Note: Normally this would be in the form of an actual database */
  private List<Student> students;

  @Override
  public final Optional<Student> find(final int studentId) {

    /* Compare with existing students */
    for (final Student student : this.students) {

      /* Check if student is found */
      if (student.getStudentId() == studentId) {

        return Optional.of(student);
      }
    }

    /* Return empty value */
    return Optional.empty();
  }

  @Override
  public final void update(final Student studentToBeUpdated) throws DataMapperException {


    /* Check with existing students */
    if (this.students.contains(studentToBeUpdated)) {

      /* Get the index of student in list */
      final int index = this.students.indexOf(studentToBeUpdated);

      /* Update the student in list */
      this.students.set(index, studentToBeUpdated);

    } else {

      /* Throw user error */
      throw new DataMapperException("Student [" + studentToBeUpdated.getName() + "] is not found");
    }
  }

  @Override
  public final void insert(final Student studentToBeInserted) throws DataMapperException {

    /* Check with existing students */
    if (!this.students.contains(studentToBeInserted)) {

      /* Add student in list */
      this.students.add(studentToBeInserted);

    } else {

      /* Throw user error */
      throw new DataMapperException("Student already [" + studentToBeInserted.getName() + "] exists");
    }
  }

  @Override
  public final void delete(final Student studentToBeDeleted) throws DataMapperException {

    /* Check with existing students */
    if (this.students.contains(studentToBeDeleted)) {

      /* Delete the student from list */
      this.students.remove(studentToBeDeleted);

    } else {

      /* Throw user error */
      throw new DataMapperException("Student [" + studentToBeDeleted.getName() + "] is not found");
    }
  }
}
