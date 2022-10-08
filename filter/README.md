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

criteria.Criteria pattern

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
Here we have criteria.Criteria interface to be used for implementing filter options and implementation.
```java
public interface criteria.Criteria {

    List<domain.Student> meetCriteria(List<domain.Student> students);
}

public class criteria.CriteriaMale implements criteria.Criteria {

    @Override
    public List<domain.Student> meetCriteria(final List<domain.Student> students) {
        return students.stream()
                .filter(domain.Student::isMale)
                .collect(Collectors.toList());
    }
}

public class criteria.CriteriaGPAHigherThan3 implements criteria.Criteria {

    public static final double standardGpa = 3.0;

    @Override
    public List<domain.Student> meetCriteria(final List<domain.Student> students) {
        return students.stream()
                .filter(student -> student.isGpaHigherThan(standardGpa))
                .collect(Collectors.toList());
    }
}

public class criteria.AndCriteria implements criteria.Criteria {

    private final criteria.Criteria criteria;
    private final criteria.Criteria anotherCriteria;

    public criteria.AndCriteria(final criteria.Criteria criteria, final criteria.Criteria anotherCriteria) {
        this.criteria = criteria;
        this.anotherCriteria = anotherCriteria;
    }

    @Override
    public List<domain.Student> meetCriteria(final List<domain.Student> students) {
        return anotherCriteria.meetCriteria(criteria.meetCriteria(students));
    }
}

public class criteria.OrCriteria implements criteria.Criteria {

    private final criteria.Criteria criteria;
    private final criteria.Criteria anotherCriteria;

    public criteria.OrCriteria(final criteria.Criteria criteria, final criteria.Criteria anotherCriteria) {
        this.criteria = criteria;
        this.anotherCriteria = anotherCriteria;
    }

    @Override
    public List<domain.Student> meetCriteria(final List<domain.Student> students) {
        final List<domain.Student> filteredStudents1 = criteria.meetCriteria(students);
        final List<domain.Student> filteredStudents2 = anotherCriteria.meetCriteria(students);

        return Stream.of(filteredStudents1, filteredStudents2)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
```
This is a class that represents student
```java
public class domain.Student {

    private final double gpa;
    private final domain.Gender gender;
    private final String name;

    public domain.Student(final double gpa, final domain.Gender gender, final String name) {
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
        if (!(o instanceof domain.Student)) {
            return false;
        }
        domain.Student student = (domain.Student) o;
        return Double.compare(student.gpa, gpa) == 0 && gender == student.gender && Objects.equals(name,
                student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gpa, gender, name);
    }

    @Override
    public String toString() {
        return "domain.Student{" +
                "gpa=" + gpa +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                '}';
    }
}

public enum domain.Gender {
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
import criteria.AndCriteria;
import criteria.Criteria;
import criteria.CriteriaGPAHigherThan3;
import criteria.CriteriaMale;
import criteria.OrCriteria;
import domain.Gender;
import domain.Student;

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
domain.Student{gpa=3.5, gender=MALE, name='student1'}
domain.Student{gpa=2.0, gender=MALE, name='student2'}

criteriaGPAHigherThan3
domain.Student{gpa=3.5, gender=MALE, name='student1'}
domain.Student{gpa=3.5, gender=FEMALE, name='student3'}

andCriteria
domain.Student{gpa=3.5, gender=MALE, name='student1'}

orCriteria
domain.Student{gpa=3.5, gender=MALE, name='student1'}
domain.Student{gpa=2.0, gender=MALE, name='student2'}
domain.Student{gpa=3.5, gender=FEMALE, name='student3'}
```
## Class diagram
![filter pattern class diagram](./etc/filter.png)

## Applicability

Use the Filter pattern when

- Filtering object through multiple options
- There is a possibility that filtering options will be added

## Tutorials

[Filter Pattern Tutorial](https://www.tutorialspoint.com/design_pattern/filter_pattern.htm)


## Credits
[Design Patterns - Filter Pattern](https://www.tutorialspoint.com/design_pattern/filter_pattern.htm)  
[Filter Pattern in Java](https://www.geeksforgeeks.org/filter-pattern-in-java/)
