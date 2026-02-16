public class Attendance
{
    private int age;
    private String name;

    public Attendance()
    {
        this.age=0;
        this.name="";
    }

    public Attendance(int age, String name)
    {
        this.age=age;
        this.name=name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age=age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    @Override
    public String toString()
    {
        return "Student{name='"+name+"', age=" +age+ "}";
    }
}