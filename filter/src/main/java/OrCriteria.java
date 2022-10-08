import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrCriteria implements Criteria {

    private final Criteria criteria;
    private final Criteria anotherCriteria;

    public OrCriteria(final Criteria criteria, final Criteria anotherCriteria) {
        this.criteria = criteria;
        this.anotherCriteria = anotherCriteria;
    }

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        final List<Student> filteredStudents1 = criteria.meetCriteria(students);
        final List<Student> filteredStudents2 = anotherCriteria.meetCriteria(students);

        return Stream.of(filteredStudents1, filteredStudents2)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
