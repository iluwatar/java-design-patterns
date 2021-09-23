package memento;

import java.util.Stack;

class Test {
    public static void main(String[] args) {
        Stack<Memento> states = new Stack<>();
        School school = new School();
        states.push(school.getMemento());
        System.out.println(school);

        school.timePasses();
        states.push(school.getMemento());
        System.out.println(school);

        school.timePasses();
        states.push(school.getMemento());
        System.out.println(school);

        school.timePasses();
        states.push(school.getMemento());
        System.out.println(school);

        while (states.size() > 0) {
            school.setMemento(states.pop());
            System.out.println(school);
        }
    }
}

// 备忘录
public class Memento {
    private int size;// 学校大小
    private int num;// 学生人数

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

// 发起者
class School {
    private int size = 1;// 学校大小
    private int num = 10;// 学生人数

    public void timePasses() {
        size *= 2;
        num *= 8;
    }

    Memento getMemento() {
        var state = new Memento();
        state.setSize(size);
        state.setNum(num);
        return state;
    }

    void setMemento(Memento memento) {
        this.size = memento.getSize();
        this.num = memento.getNum();
    }

    @Override
    public String toString() {
        return "School{" +
                "size=" + size +
                ", num=" + num +
                '}';
    }
}
