package criteria;

import domain.Student;
import java.util.List;
import java.util.stream.Collectors;

public class CriteriaMale implements Criteria {

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        return students.stream()
                .filter(Student::isMale)
                .collect(Collectors.toList());
    }
}
