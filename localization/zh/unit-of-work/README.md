---
layout: pattern
title: Unit Of Work
folder: unit-of-work
permalink: /patterns/unit-of-work/zh

categories: Architectural
language: zh
tags:
 - Data access
 - Performance
---

## 目的
当一个业务交易完成时，所有的更新都作为一个大的工作单元被发送
一次性坚持以最大限度地减少数据库往返。

## 解释

真实世界的例子

> 我们有一个包含学生信息的数据库。管理员遍布全国
> 不断更新此信息并导致数据库服务器的高负载。为了使
> 加载更易于管理，我们将工作单元模式应用于批量发送许多小更新。

简单来说

> Unit of Work 将多个小型数据库更新合并成一个批次，以优化更新数量
> 往返。

[MartinFowler.com](https://martinfowler.com/eaaCatalog/unitOfWork.html) 提到
> 维护受业务交易影响的对象列表并协调写出
> 更改和并发问题的解决。

**程序示例**

这是被持久化到数据库的 `Student` 实体。

```java
public class Student {
  private final Integer id;
  private final String name;
  private final String address;

  public Student(Integer id, String name, String address) {
    this.id = id;
    this.name = name;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }
}
```

实现的本质是“StudentRepository”实现了工作单元模式。
它维护需要完成的数据库操作（`context`）的映射以及何时`commit`
称为它在单个批次中应用它们。
```java
public interface IUnitOfWork<T> {
    
  String INSERT = "INSERT";
  String DELETE = "DELETE";
  String MODIFY = "MODIFY";

  void registerNew(T entity);

  void registerModified(T entity);

  void registerDeleted(T entity);

  void commit();
}

@Slf4j
public class StudentRepository implements IUnitOfWork<Student> {

  private final Map<String, List<Student>> context;
  private final StudentDatabase studentDatabase;

  public StudentRepository(Map<String, List<Student>> context, StudentDatabase studentDatabase) {
    this.context = context;
    this.studentDatabase = studentDatabase;
  }

  @Override
  public void registerNew(Student student) {
    LOGGER.info("Registering {} for insert in context.", student.getName());
    register(student, IUnitOfWork.INSERT);
  }

  @Override
  public void registerModified(Student student) {
    LOGGER.info("Registering {} for modify in context.", student.getName());
    register(student, IUnitOfWork.MODIFY);

  }

  @Override
  public void registerDeleted(Student student) {
    LOGGER.info("Registering {} for delete in context.", student.getName());
    register(student, IUnitOfWork.DELETE);
  }

  private void register(Student student, String operation) {
    var studentsToOperate = context.get(operation);
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
    LOGGER.info("Commit started");
    if (context.containsKey(IUnitOfWork.INSERT)) {
      commitInsert();
    }

    if (context.containsKey(IUnitOfWork.MODIFY)) {
      commitModify();
    }
    if (context.containsKey(IUnitOfWork.DELETE)) {
      commitDelete();
    }
    LOGGER.info("Commit finished.");
  }

  private void commitInsert() {
    var studentsToBeInserted = context.get(IUnitOfWork.INSERT);
    for (var student : studentsToBeInserted) {
      LOGGER.info("Saving {} to database.", student.getName());
      studentDatabase.insert(student);
    }
  }

  private void commitModify() {
    var modifiedStudents = context.get(IUnitOfWork.MODIFY);
    for (var student : modifiedStudents) {
      LOGGER.info("Modifying {} to database.", student.getName());
      studentDatabase.modify(student);
    }
  }

  private void commitDelete() {
    var deletedStudents = context.get(IUnitOfWork.DELETE);
    for (var student : deletedStudents) {
      LOGGER.info("Deleting {} to database.", student.getName());
      studentDatabase.delete(student);
    }
  }
}
```

最后，这里是我们如何使用 `StudentRepository` 和 `commit` 事务。
```java
    studentRepository.registerNew(ram);
    studentRepository.registerModified(shyam);
    studentRepository.registerDeleted(gopi);
    studentRepository.commit();
```

## 类图

![alt text](etc/unit-of-work.urm.png "unit-of-work")

## 适用性
在以下情况下使用工作单元模式

* 优化数据库事务所花费的时间。
* 将更改作为一个工作单元发送到数据库，以确保事务的原子性。
* 减少数据库调用次数。

## 教程

* [Repository and Unit of Work Pattern](https://www.programmingwithwolfgang.com/repository-and-unit-of-work-pattern/)
* [Unit of Work - a Design Pattern](https://mono.software/2017/01/13/unit-of-work-a-design-pattern/)

## 鸣谢

* [Design Pattern - Unit Of Work Pattern](https://www.codeproject.com/Articles/581487/Unit-of-Work-Design-Pattern)
* [Unit Of Work](https://martinfowler.com/eaaCatalog/unitOfWork.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
