package criteria;

import domain.Student;
import java.util.List;

public interface Criteria {

    List<Student> meetCriteria(List<Student> students);
}
