class Singleton{
private static Singleton single_instance=null;
public String s;
private Singleton()
{
s= "Hello world";
}
public static Singleton getInstance(){
if(single_instance==null)
	single_instance=new Singleton();
return single_instance;
}
}
public class SingletonPattern{
public static void main(String ars[])
{
	Singleton x= Singleton.getInstance();
	Singleton y= Singleton.getInstance();
	System.out.println("x hash code "+x.hashCode());
	System.out.println("y hash code "+y.hashCode());
	
}
}
