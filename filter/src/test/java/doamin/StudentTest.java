package doamin;

import static org.junit.jupiter.api.Assertions.*;

import domain.Gender;
import domain.Student;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void checkStudentGenderIsMaleOrNot() {
        final Student student = new Student(3.0, Gender.MALE, "studentName");

        assertTrue(student.isMale());
    }

    @Test
    void checkStudentGpaIsHigherThan3_0OrNot() {
        final Student student = new Student(3.0, Gender.MALE, "studentName");

        assertTrue(student.isGpaHigherThan(3.0));
    }
}
