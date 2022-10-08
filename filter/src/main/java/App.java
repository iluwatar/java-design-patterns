package com.iluwatar.filter;

import java.util.List;

public class App {

    public static void main(String[] args) {
        final domain.Student student1 = new domain.Student(3.5, domain.Gender.MALE, "student1");
        final domain.Student student2 = new domain.Student(2.0, domain.Gender.MALE, "student2");
        final domain.Student student3 = new domain.Student(3.5, domain.Gender.FEMALE, "student3");
        final domain.Student student4 = new domain.Student(2.5, domain.Gender.FEMALE, "student4");
        final List<domain.Student> students = List.of(student1, student2, student3, student4);

        System.out.println("criterialMale");
        final criteria.Criteria criteriaMale = new criteria.CriteriaMale();
        criteriaMale.meetCriteria(students)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("criteriaGPAHigherThan3");
        final criteria.Criteria criteriaGpaHigherThan3 = new criteria.CriteriaGPAHigherThan3();
        criteriaGpaHigherThan3.meetCriteria(students)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("andCriteria");
        final criteria.Criteria andCriteria = new criteria.AndCriteria(criteriaMale, criteriaGpaHigherThan3);
        andCriteria.meetCriteria(students)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("orCriteria");
        final criteria.Criteria orCriteria = new criteria.OrCriteria(criteriaMale, criteriaGpaHigherThan3);
        orCriteria.meetCriteria(students)
                .forEach(System.out::println);
    }
}
