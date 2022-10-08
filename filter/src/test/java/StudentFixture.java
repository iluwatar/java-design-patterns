import java.util.List;

public class StudentFixture {

    public static final Student MALE_GPA_3_5 = new Student(3.5, Gender.MALE, "student1");
    public static final Student MALE_GPA_2_0 = new Student(2.0, Gender.MALE, "student2");
    public static final Student FEMALE_GPA_3_5 = new Student(3.5, Gender.FEMALE, "student3");
    public static final Student FEMALE_GPA_2_5 = new Student(2.5, Gender.FEMALE, "student4");

    public static final List<Student> students = List.of(MALE_GPA_3_5, MALE_GPA_2_0, FEMALE_GPA_3_5, FEMALE_GPA_2_5);
}
