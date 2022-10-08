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
