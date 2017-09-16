/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Piyush Chaudhari
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.iluwatar.unitofwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link StudentRepository} Student database repository.
 * supports unit of work for student data.
 */
public class StudentRepository implements IUnitOfWork<Student> {
  private Map<String, List<Student>> context;
  private StudentDatabase studentDatabase;

  /**
   * @param context         set of operations to be perform during commit.
   * @param studentDatabase Database for student records.
   */
  public StudentRepository(Map<String, List<Student>> context, StudentDatabase studentDatabase) {
    this.context = context;
    this.studentDatabase = studentDatabase;
  }

  @Override
  public void registerNew(Student student) {
    register(student, IUnitOfWork.INSERT);
  }

  @Override
  public void registerModified(Student student) {
    register(student, IUnitOfWork.MODIFY);

  }

  @Override
  public void registerDeleted(Student student) {
    register(student, IUnitOfWork.DELETE);
  }

  private void register(Student student, String operation) {
    List<Student> studentsToOperate = context.get(operation);
    if (studentsToOperate == null) {
      studentsToOperate = new ArrayList<>();
    }
    studentsToOperate.add(student);
    context.put(operation, studentsToOperate);
  }

  @Override
  public void commit() {
    if (context == null || context.size() == 0) {
      return;
    }
    if (context.containsKey(IUnitOfWork.INSERT)) {
      commitInsert();
    }

    if (context.containsKey(IUnitOfWork.MODIFY)) {
      commitModify();
    }
    if (context.containsKey(IUnitOfWork.DELETE)) {
      commitDelete();
    }
  }

  private void commitDelete() {
    List<Student> deletedStudents = context.get(IUnitOfWork.DELETE);
    for (Student student : deletedStudents) {
      studentDatabase.delete(student);
    }
  }

  private void commitModify() {
    List<Student> modifiedStudents = context.get(IUnitOfWork.MODIFY);
    for (Student student : modifiedStudents) {
      studentDatabase.modify(student);
    }
  }

  private void commitInsert() {
    List<Student> studentsToBeInserted = context.get(IUnitOfWork.INSERT);
    for (Student student : studentsToBeInserted) {
      studentDatabase.insert(student);
    }
  }
}
