package PACKAGE_NAME;

public class App {

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
