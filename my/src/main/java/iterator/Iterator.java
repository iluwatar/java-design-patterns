package iterator;

import java.util.ArrayList;
import java.util.List;

class Test {
    public static void main(String[] args) {
        School school = new School();
        for (int i = 0; i < 100; i++) {
            school.add(new Student("" + i, i));
        }
        Iterator iterator = school.iterator(10);
        while (iterator.hasNext()) {
            Student next = iterator.next();
            System.out.println(next);
        }
    }
}

public class Iterator {

    private final School school;
    private final int level;
    private int currentIndex = -1;

    public Iterator(School school, int level) {
        this.school = school;
        this.level = level;
    }

    // 是否有下一个
    public boolean hasNext() {
        int nextIndex = findNextIndex();
        return nextIndex != -1;
    }

    // 下一个值，没有则返回null
    public Student next() {
        currentIndex = findNextIndex();
        if (currentIndex != -1) {
            // 有值
            return school.getList().get(currentIndex);
        } else {
            // 没值
            return null;
        }
    }

    // 下一个位置，没有则返回-1
    private int findNextIndex() {
        List<Student> list = school.getList();
        int size = list.size();
        if (currentIndex < size) {
            // 在集合内，找到返回，没找到返回-1
            for (int nextIndex = currentIndex + 1; nextIndex < size; nextIndex++) {
                Student student = list.get(nextIndex);
                if (student.getLevel() % level == 0) {
                    return nextIndex;
                }
            }
        }
        return -1;
    }
}

class School {
    private final List<Student> list = new ArrayList<>();

    public void add(Student student) {
        list.add(student);
    }

    public Iterator iterator(int level) {
        return new Iterator(this, level);
    }

    public List<Student> getList() {
        return list;
    }
}

class Student {
    private final String name;
    private final int level;

    public Student(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", level=" + level +
                '}';
    }

    public int getLevel() {
        return level;
    }
}