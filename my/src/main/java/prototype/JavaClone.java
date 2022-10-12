package prototype;

class JavaCloneTest {
    public static void main(String[] args) {
        Other rawOther = new Other("lisi");
        CloneUser rawUser = new CloneUser("zhangsan", 20, rawOther);
        CloneUser cloneUser = rawUser.clone();

        System.out.println(rawUser.name.equals(cloneUser.name));
        System.out.println(rawUser.age == cloneUser.age);

        System.out.println(rawOther.name.equals(cloneUser.other.name));
        System.out.println(rawOther == cloneUser.other);
    }
}

class CloneUser implements Cloneable {
    String name;
    int age;
    Other other;

    public CloneUser(String name, int age, Other other) {
        this.name = name;
        this.age = age;
        this.other = other;
    }

    // 父类clone发方法，修改了返回值为具体的，返回内容进行了强转，去掉了抛出的异常。
    // 必须要重写clone方法，否则在外部调用不能访问父类的protected的方法，如clone方法。
    @Override
    protected CloneUser clone() {
        try {
            // 浅拷贝，基本数据类型直接拷贝，非基本数据类型拷贝的是引用（一个修改，另一个也变）。
            // 深拷贝，需要自己维护非基本数据类型的拷贝，不要拷贝引用的效果，要拷贝为一个新的对象;。
            CloneUser cloneUser = (CloneUser) super.clone();
            // 深拷贝
            cloneUser.other = new Other(cloneUser.other.name);
            return cloneUser;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Other {
    String name;

    public Other(String name) {
        this.name = name;
    }
}