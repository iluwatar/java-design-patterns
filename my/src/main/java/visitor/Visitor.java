package visitor;

import java.util.Arrays;

class Test {
    public static void main(String[] args) {
        SchoolWorker worker = new Principal(
                new Teacher(new Student(), new Student(), new Student()),
                new Teacher(new Student(), new Student(), new Student())
        );
        worker.accept(new HelloVisitor());
        worker.accept(new DonationsVisitor());
    }
}

// 访问者
public interface Visitor {
    void visitPrincipal(Principal principal);

    void visitTeacher(Teacher teacher);

    void visitStudent(Student student);
}

// 问好，访问者
class HelloVisitor implements Visitor {

    @Override
    public void visitPrincipal(Principal principal) {
        System.out.println("校长好，" + principal);
    }

    @Override
    public void visitTeacher(Teacher teacher) {
        System.out.println("老师好，" + teacher);
    }

    @Override
    public void visitStudent(Student student) {
        System.out.println("同学好，" + student);
    }
}

// 捐款，访问者
class DonationsVisitor implements Visitor {

    @Override
    public void visitPrincipal(Principal principal) {
        System.out.println("校长捐款，100，" + principal);
    }

    @Override
    public void visitTeacher(Teacher teacher) {
        System.out.println("老师捐款，50，" + teacher);
    }

    @Override
    public void visitStudent(Student student) {
        System.out.println("学生捐款，10，" + student);
    }
}

// 校长
class Principal extends SchoolWorker {
    public Principal(SchoolWorker... children) {
        super(children);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPrincipal(this);
        super.accept(visitor);
    }
}

// 老师
class Teacher extends SchoolWorker {
    public Teacher(SchoolWorker... children) {
        super(children);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitTeacher(this);
        super.accept(visitor);
    }
}

// 学生
class Student extends SchoolWorker {
    public Student(SchoolWorker... children) {
        super(children);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitStudent(this);
        super.accept(visitor);
    }
}

// 学校工作者
class SchoolWorker {

    private final SchoolWorker[] children;

    public SchoolWorker(SchoolWorker... children) {
        this.children = children;
    }

    public void accept(Visitor visitor) {
        Arrays.stream(children).forEach(child -> child.accept(visitor));
    }
}
