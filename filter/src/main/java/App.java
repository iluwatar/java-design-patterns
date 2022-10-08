/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
