abstract class Veichle{
public abstract int getWheel();
public String toString(){
return "Wheel:"+ this.getWheel();
}
}
class Car extends Veichle{
	int wheel;
	Car(int wheel){
		this.wheel=wheel;
	}
	@Override
	public int getWheel(){
		return this.wheel;
	}
}
class Bike extends Veichle{
	int wheel;
	Bike(int wheel){
		this.wheel=wheel;
	}
	@Override
	public int getWheel(){
		return this.wheel;
	}
}
class Veichlefactory{
	public static Veichle getInstance(String type,int wheel){
		if(wheel==4){
			return new Car(wheel);
		}else if(wheel==2){
			return new Bike(wheel);
		}
		return null;
	}
}
public class FactoryExample{
	public static void  main(String args[])
	{
		Veichle car= Veichlefactory.getInstance("car",4);
		System.out.println(car);
		Veichle bike= Veichlefactory.getInstance("bike",2);
		System.out.println(bike);
	}
}
