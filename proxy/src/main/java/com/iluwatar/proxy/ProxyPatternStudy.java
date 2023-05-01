package com.iluwatar.proxy;

public class ProxyPatternStudy {
    // Proxy Pattern 실습
    // Proxy (일종의 비서 역할) 는 우리말로 대리자, 대변인 이라는 뜻
    // Proxy 는 어떤 일을 대신 시키는 것이라는 뜻 (OCP / DIP 설계 원칙이 녹아져 있음)
    // 구체적으로 인터페이스를 사용하고 실행시킬 클래스에 대한 객체가 들어갈 자리에 대리자 객체를 대신 투입
    // Proxy 는 결국 직접 실행 메서드를 호출하는 것을 피하면서 취하고 싶었던 것은 흐름 제어임
    // 흐름제어가 필요한 이유는 게임을 예를 들어보면 유저가 죽고나고 다시 살아날 때 로딩 시간이 걸림
    // 데이터가 큰 이미지나 그래픽을 로딩하는데 시간이 걸리기 때문 이럴 경우에 유저는 불편함을 만듦
    // 이럴 떄 프록시는 제어 흐름을 통하여 큰 데이터가 로딩 될 때까지 현재끼지 완료 된 것을 우선적으로 보여줌



    // 중요한 것은 흐름제어만 할 뿐 결과 값을 조작하거나 변경 시키면 안됨 !!!
    public static void main(String[] args) {
        School sch = new SchoolProxy(new SchoolImpl());

        sch.move(new StudentProxy("name1" , "1"));
        sch.move(new StudentProxy("name2" , "2"));
        sch.move(new StudentProxy("name3" , "3"));
    }
}

// School Proxy Pattern !!!!!!!!
class SchoolProxy implements School {
    private School school;

    SchoolProxy(School school) {
        this.school = school;
    }

    @Override
    public void move(StudentProxy stu) {
        System.out.println("THIS IS SCHOOL PROXY PATTERN STUDENT MOVE NAME : "
                + stu.getStuName() + " STUDENT MOVE AGE : " + stu.getStuAge());
        // School 생성 된 객체에 메서드를 전달
        school.move(stu);
    }
}

class SchoolImpl implements School {
    @Override
    public void move(StudentProxy stu) {
        System.out.println("THIS IS SHCOOLIMPL MOVE METHOD !!!");
    }
}

interface School {
    void move(StudentProxy stu);
}

class StudentProxy {
    String stuName;
    String stuAge;

    StudentProxy(String stuName, String stuAge) {
        this.stuName = stuName;
        this.stuAge = stuAge;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuAge() {
        return stuAge;
    }
    public void setStuAge(String stuAge) {
        this.stuAge = stuAge;
    }
}

