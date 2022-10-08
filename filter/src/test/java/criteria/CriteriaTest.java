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
package criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import doamin.StudentFixture;
import domain.Student;
import java.util.List;
import org.junit.jupiter.api.Test;

class CriteriaTest {

    @Test
    void CriteriaMale() {
        final List<Student> expected = List.of(StudentFixture.MALE_GPA_3_5, StudentFixture.MALE_GPA_2_0);
        final List<Student> actual = new CriteriaMale().meetCriteria(StudentFixture.students);

        assertEquals(expected, actual);
    }

    @Test
    void CriteriaGPAHigherThan3() {
        final List<Student> expected = List.of(StudentFixture.MALE_GPA_3_5, StudentFixture.FEMALE_GPA_3_5);
        final List<Student> actual = new CriteriaGPAHigherThan3().meetCriteria(StudentFixture.students);

        assertEquals(expected, actual);
    }

    @Test
    void AndCriteria() {
        final List<Student> expected = List.of(StudentFixture.MALE_GPA_3_5);
        final Criteria criteriaMale = new CriteriaMale();
        final Criteria criteriaGpaHigherThan3 = new CriteriaGPAHigherThan3();
        final Criteria andCriterial = new AndCriteria(criteriaMale, criteriaGpaHigherThan3);
        final List<Student> actual = andCriterial.meetCriteria(StudentFixture.students);

        assertEquals(expected, actual);
    }

    @Test
    void orCriteria() {
        final List<Student> expected = List.of(StudentFixture.MALE_GPA_3_5, StudentFixture.MALE_GPA_2_0,
                StudentFixture.FEMALE_GPA_3_5);
        final Criteria criteriaMale = new CriteriaMale();
        final Criteria criteriaGpaHigherThan3 = new CriteriaGPAHigherThan3();
        final Criteria orCriterial = new OrCriteria(criteriaMale, criteriaGpaHigherThan3);
        final List<Student> actual = orCriterial.meetCriteria(StudentFixture.students);

        assertEquals(expected, actual);
    }
}
