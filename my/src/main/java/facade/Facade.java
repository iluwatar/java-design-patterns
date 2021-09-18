package facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Test {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.startDay();
        facade.learn();
        facade.endDay();
    }
}

public class Facade {
    private final List<SchoolWorker> workers = new ArrayList<>();

    public Facade() {
        workers.add(new Teacher("数学老师"));
        workers.add(new Student("张三"));
        workers.add(new Student("李四"));
        workers.add(new Student("王五"));
    }

    public void startDay() {
        makeActions(SchoolWorker.Action.WAKE_UP, SchoolWorker.Action.GO_SCHOOL);
    }

    public void learn() {
        makeActions(SchoolWorker.Action.LEARNING);
    }

    public void endDay() {
        makeActions(SchoolWorker.Action.GO_HOME, SchoolWorker.Action.GO_TO_SLEEP);
    }

    private void makeActions(SchoolWorker.Action... actions) {
        workers.forEach(schoolWorker -> schoolWorker.action(actions));
    }
}

class Teacher extends SchoolWorker {

    public Teacher(String name) {
        super(name);
    }

    @Override
    public void learning() {
        super.learning();
        println("讲课中");
    }
}

class Student extends SchoolWorker {

    public Student(String name) {
        super(name);
    }

    @Override
    public void learning() {
        super.learning();
        println("学习中");
    }
}

class SchoolWorker {
    private final String name;

    public SchoolWorker(String name) {
        this.name = name;
    }

    private void action(Action action) {
        switch (action) {
            case WAKE_UP:
                wakeUp();
                break;
            case GO_SCHOOL:
                goSchool();
                break;
            case LEARNING:
                learning();
                break;
            case GO_HOME:
                goHome();
                break;
            case GO_TO_SLEEP:
                goToSleep();
                break;
            default:
                println("Undefined action");
                break;
        }
    }

    public void action(Action... actions) {
        Arrays.stream(actions).forEach(this::action);
    }

    public void wakeUp() {
        println("起床");
    }

    public void goSchool() {
        println("去学校");
    }

    public void learning() {

    }

    public void goHome() {
        println("回家");
    }

    public void goToSleep() {
        println("去睡觉");
    }

    public void println(String str) {
        System.out.println(name + " " + str);
    }

    enum Action {
        WAKE_UP, GO_SCHOOL, LEARNING, GO_HOME, GO_TO_SLEEP
    }
}
