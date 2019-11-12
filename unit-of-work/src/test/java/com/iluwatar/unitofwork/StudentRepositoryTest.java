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

package com.iluwatar.unitofwork;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * tests {@link StudentRepository}
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentRepositoryTest {
  private final Student student1 = new Student(1, "Ram", "street 9, cupertino");
  private final Student student2 = new Student(1, "Sham", "Z bridge, pune");

  private Map<String, List<Student>> context;
  @Mock
  private StudentDatabase studentDatabase;
  private StudentRepository studentRepository;

  @Before
  public void setUp() {
    context = new HashMap<>();
    studentRepository = new StudentRepository(context, studentDatabase);
  }

  @Test
  public void shouldSaveNewStudentWithoutWritingToDb() {
    studentRepository.registerNew(student1);
    studentRepository.registerNew(student2);

    assertEquals(2, context.get(IUnitOfWork.INSERT).size());
    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  public void shouldSaveDeletedStudentWithoutWritingToDb() {
    studentRepository.registerDeleted(student1);
    studentRepository.registerDeleted(student2);

    assertEquals(2, context.get(IUnitOfWork.DELETE).size());
    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  public void shouldSaveModifiedStudentWithoutWritingToDb() {
    studentRepository.registerModified(student1);
    studentRepository.registerModified(student2);

    assertEquals(2, context.get(IUnitOfWork.MODIFY).size());
    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  public void shouldSaveAllLocalChangesToDb() {
    context.put(IUnitOfWork.INSERT, List.of(student1));
    context.put(IUnitOfWork.MODIFY, List.of(student1));
    context.put(IUnitOfWork.DELETE, List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, times(1)).insert(student1);
    verify(studentDatabase, times(1)).modify(student1);
    verify(studentDatabase, times(1)).delete(student1);
  }

  @Test
  public void shouldNotWriteToDbIfContextIsNull() {
    var studentRepository = new StudentRepository(null, studentDatabase);

    studentRepository.commit();

    verifyNoMoreInteractions(studentDatabase);
  }

  @Test
  public void shouldNotWriteToDbIfNothingToCommit() {
    var studentRepository = new StudentRepository(new HashMap<>(), studentDatabase);

    studentRepository.commit();

    verifyZeroInteractions(studentDatabase);
  }

  @Test
  public void shouldNotInsertToDbIfNoRegisteredStudentsToBeCommitted() {
    context.put(IUnitOfWork.MODIFY, List.of(student1));
    context.put(IUnitOfWork.DELETE, List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, never()).insert(student1);
  }

  @Test
  public void shouldNotModifyToDbIfNotRegisteredStudentsToBeCommitted() {
    context.put(IUnitOfWork.INSERT, List.of(student1));
    context.put(IUnitOfWork.DELETE, List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, never()).modify(student1);
  }

  @Test
  public void shouldNotDeleteFromDbIfNotRegisteredStudentsToBeCommitted() {
    context.put(IUnitOfWork.INSERT, List.of(student1));
    context.put(IUnitOfWork.MODIFY, List.of(student1));

    studentRepository.commit();

    verify(studentDatabase, never()).delete(student1);
  }
}