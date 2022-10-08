package criteria;

import domain.Student;
import java.util.List;
import java.util.stream.Collectors;

public class CriteriaGPAHigherThan3 implements Criteria {

    public static final double standardGpa = 3.0;

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        return students.stream()
                .filter(student -> student.isGpaHigherThan(standardGpa))
                .collect(Collectors.toList());
    }
}
