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
package domain;

import java.util.Objects;

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
        return "domain.Student{" +
                "gpa=" + gpa +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                '}';
    }
}
