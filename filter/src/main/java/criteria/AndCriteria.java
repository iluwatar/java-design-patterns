package criteria;

import domain.Student;
import java.util.List;

public class AndCriteria implements Criteria {

    private final Criteria criteria;
    private final Criteria anotherCriteria;

    public AndCriteria(final Criteria criteria, final Criteria anotherCriteria) {
        this.criteria = criteria;
        this.anotherCriteria = anotherCriteria;
    }

    @Override
    public List<Student> meetCriteria(final List<Student> students) {
        return anotherCriteria.meetCriteria(criteria.meetCriteria(students));
    }
}
