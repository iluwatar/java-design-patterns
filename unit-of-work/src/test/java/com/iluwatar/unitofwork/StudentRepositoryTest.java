/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.unitofwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * tests {@link StudentRepository}
 */

class StudentRepositoryTest {
  private final Student student1 = new Student(1, "Ram", "street 9, cupertino");
  private final Student student2 = new Student(1, "Sham", "Z bridge, pune");

  private final Map<String, List<Student>> context = new HashMap<>();
  private final StudentDatabase studentDatabase = mock(StudentDatabase.class);
  private final StudentRepository studentRepository = new StudentRepository(context, studentDatabase);;

  @Test
  void shouldSaveNewStudentWithoutWritingToDb() {
    studentRepository.registerNew(student1);
    studentRepository.registerNew(student2);

    assertEquals(2, context.get(UnitActions.INSERT.getActionValue()).size());
    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  void shouldSaveDeletedStudentWithoutWritingToDb() {
    studentRepository.registerDeleted(student1);
    studentRepository.registerDeleted(student2);

    assertEquals(2, context.get(UnitActions.DELETE.getActionValue()).size());
    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  void shouldSaveModifiedStudentWithoutWritingToDb() {
    studentRepository.registerModified(student1);
    studentRepository.registerModified(student2);

    assertEquals(2, context.get(UnitActions.MODIFY.getActionValue()).size());
    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  void shouldSaveAllLocalChangesToDb() {
    context.put(UnitActions.INSERT.getActionValue(), List.of(student1));
    context.put(UnitActions.MODIFY.getActionValue(), List.of(student1));
    context.put(UnitActions.DELETE.getActionValue(), List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, times(1)).insert(student1);
    verify(studentDatabase, times(1)).modify(student1);
    verify(studentDatabase, times(1)).delete(student1);
  }

  @Test
  void shouldNotWriteToDbIfContextIsNull() {
    var studentRepository = new StudentRepository(null, studentDatabase);

    studentRepository.commit();

    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  void shouldNotWriteToDbIfNothingToCommit() {
    var studentRepository = new StudentRepository(new HashMap<>(), studentDatabase);

    studentRepository.commit();

    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  void shouldNotInsertToDbIfNoRegisteredStudentsToBeCommitted() {
    context.put(UnitActions.MODIFY.getActionValue(), List.of(student1));
    context.put(UnitActions.DELETE.getActionValue(), List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, never()).insert(student1);
  }

  @Test
  void shouldNotModifyToDbIfNotRegisteredStudentsToBeCommitted() {
    context.put(UnitActions.INSERT.getActionValue(), List.of(student1));
    context.put(UnitActions.DELETE.getActionValue(), List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, never()).modify(student1);
  }

  @Test
  void shouldNotDeleteFromDbIfNotRegisteredStudentsToBeCommitted() {
    context.put(UnitActions.INSERT.getActionValue(), List.of(student1));
    context.put(UnitActions.MODIFY.getActionValue(), List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, never()).delete(student1);
  }
}
