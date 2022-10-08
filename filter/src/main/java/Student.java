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
        return "Student{" +
                "gpa=" + gpa +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                '}';
    }
}
