public class Student {
    private int age;
    private String name;

    // Default constructor
    public Student() {
        this.age = 0;
        this.name = "";
    }

    // Parameterized constructor
    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    // Getter for age
    public int getAge() {
        return age;
    }

    // Setter for age
    public void setAge(int age) {
        this.age = age;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // For easy display
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}
