package proxy;

class Test {
    public static void main(String[] args) {
//        SchoolRoom room = new ClassRoom();
        SchoolRoom room = new Proxy(new ClassRoom());
        for (int i = 0; i < 50; i++) {
            room.enter(new Student("zhang " + i));
        }
    }
}

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}

class ClassRoom implements SchoolRoom {

    public void enter(Student student) {
        System.out.println("欢迎：" + student);
    }
}

interface SchoolRoom {
    void enter(Student student);
}

public class Proxy implements SchoolRoom {
    private int count = 0;
    private final SchoolRoom schoolRoom;

    public Proxy(SchoolRoom schoolRoom) {
        this.schoolRoom = schoolRoom;
    }

    @Override
    public void enter(Student student) {
        if (count < 40) {
            this.schoolRoom.enter(student);
            count++;
        } else {
            System.out.println("教室已经满了，不能再进入了。" + student);
        }
    }
}