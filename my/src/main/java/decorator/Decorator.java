package decorator;

class Test {
    public static void main(String[] args) {
        // normaPeople
        NormaPeople normaPeople = new NormaPeople();
        System.out.println("Speed=" + normaPeople.getSpeed());
        System.out.println("MaxAge=" + normaPeople.getMaxAge());
        // 变成超人
        SupperPeople supperPeople = new SupperPeople(normaPeople);
        System.out.println("Speed=" + supperPeople.getSpeed());
        System.out.println("MaxAge=" + supperPeople.getMaxAge());
    }
}

// 人
interface People {
    int getSpeed();

    int getMaxAge();
}

class NormaPeople implements People {
    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public int getMaxAge() {
        return 100;
    }
}

class SupperPeople implements People {
    private People people;

    public SupperPeople(People people) {
        this.people = people;
    }

    @Override
    public int getSpeed() {
        return people.getSpeed() + 100;
    }

    @Override
    public int getMaxAge() {
        return people.getMaxAge() + 100;
    }
}
