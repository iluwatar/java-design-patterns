---
layout: pattern
title: Filter
folder: filter
permalink: /patterns/filter
language: en
tags:
  - Gang of Four
---
## Name / classification

Filter Pattern

## Also known as

Criteria pattern

## Intent

Filter pattern intends to decouple objects and objects' filtering conditions through logical operations.

## Explanation

This chapter consists of the following sections
### Real-world example
Consider there is a student management system, and you can search students information with various options.
### In plain words
Filter pattern is an design pattern that developers can filter objects via different criteria in a logical way.
This pattern is kind of structural pattern.

### Wikipedia says
There is no explanation in Wikipedia

### Programmatic example
Translating student management system from above.
Here we have Criteria interface to be used for implementing filter options and implementation.
```java
public interface Criteria {

    List<Student> meetCriteria(List<Student> students);
}

public class CriteriaMale implements Criteria {

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        return students.stream()
                .filter(Student::isMale)
                .collect(Collectors.toList());
    }
}

public class CriteriaGPAHigherThan3 implements Criteria {

    public static final double standardGpa = 3.0;

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        return students.stream()
                .filter(student -> student.isGpaHigherThan(standardGpa))
                .collect(Collectors.toList());
    }
}

public class AndCriteria implements Criteria {

    private final Criteria criteria;
    private final Criteria anotherCriteria;

    public AndCriteria(final Criteria criteria, final Criteria anotherCriteria) {
        this.criteria = criteria;
        this.anotherCriteria = anotherCriteria;
    }

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        return anotherCriteria.meetCriteria(criteria.meetCriteria(students));
    }
}

public class OrCriteria implements Criteria {

    private final Criteria criteria;
    private final Criteria anotherCriteria;

    public OrCriteria(final Criteria criteria, final Criteria anotherCriteria) {
        this.criteria = criteria;
        this.anotherCriteria = anotherCriteria;
    }

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        final List<Student> filteredStudents1 = criteria.meetCriteria(students);
        final List<Student> filteredStudents2 = criteria.meetCriteria(students);

        return Stream.of(filteredStudents1, filteredStudents2)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
```
This is a class that represents student
```java
public class Student {

    private final double gpa;
    private final Gender gender;
    private final String name;

    public Student(final double gpa, final Gender gender, final String name) {
        this.gpa = gpa;
        this.gender = gender;
        this.name = name;
    }

    public boolean isMale() {
        return gender.isMale();
    }

    public boolean isGpaHigherThan(final double standardGpa) {
        return gpa >= standardGpa;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Double.compare(student.gpa, gpa) == 0 && gender == student.gender && Objects.equals(name,
                student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gpa, gender, name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "gpa=" + gpa +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                '}';
    }
}

public enum Gender {
    MALE,
    FEMALE
    ;

    public boolean isMale() {
        return this.equals(MALE);
    }
}
```
This is main class
```java
public class Application {

    public static void main(String[] args) {
        final Student student1 = new Student(3.5, Gender.MALE, "student1");
        final Student student2 = new Student(2.0, Gender.MALE, "student2");
        final Student student3 = new Student(3.5, Gender.FEMALE, "student3");
        final Student student4 = new Student(2.5, Gender.FEMALE, "student4");
        final List<Student> students = List.of(student1, student2, student3, student4);

        System.out.println("criterialMale");
        final Criteria criteriaMale = new CriteriaMale();
        criteriaMale.meetCriteria(students)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("criteriaGPAHigherThan3");
        final Criteria criteriaGpaHigherThan3 = new CriteriaGPAHigherThan3();
        criteriaGpaHigherThan3.meetCriteria(students)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("andCriteria");
        final Criteria andCriteria = new AndCriteria(criteriaMale, criteriaGpaHigherThan3);
        andCriteria.meetCriteria(students)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("orCriteria");
        final Criteria orCriteria = new OrCriteria(criteriaMale, criteriaGpaHigherThan3);
        orCriteria.meetCriteria(students)
                .forEach(System.out::println);
    }
}
```
Here's the console output.
```
criterialMale
Student{gpa=3.5, gender=MALE, name='student1'}
Student{gpa=2.0, gender=MALE, name='student2'}

criteriaGPAHigherThan3
Student{gpa=3.5, gender=MALE, name='student1'}
Student{gpa=3.5, gender=FEMALE, name='student3'}

andCriteria
Student{gpa=3.5, gender=MALE, name='student1'}

orCriteria
Student{gpa=3.5, gender=MALE, name='student1'}
Student{gpa=2.0, gender=MALE, name='student2'}
```
## Class diagram

## Applicability

Use the Filter pattern when

- Filtering object through multiple options
- There is a possibility that filtering options will be added

## Tutorials

[Filter Pattern Tutorial](https://www.tutorialspoint.com/design_pattern/filter_pattern.htm)


## Credits
[Design Patterns - Filter Pattern](https://www.tutorialspoint.com/design_pattern/filter_pattern.htm)  
[Filter Pattern in Java](https://www.geeksforgeeks.org/filter-pattern-in-java/)
