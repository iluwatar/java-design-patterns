package prototype;

class Test {
    public static void main(String[] args) {
        User user = new User("zhangsan", 20);
        User clone = user.copy();
        System.out.println(user.name.equals(clone.name));
        System.out.println(user.age == clone.age);
    }
}

interface Prototype {
    Object copy();
}

class User implements Prototype {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(User user) {
        this.name = user.name;
        this.age = user.age;
    }

    @Override
    public User copy() {
        return new User(this);
    }
}