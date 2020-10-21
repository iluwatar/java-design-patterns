class Veichle{
	private String engine;
	private int wheels;
	private int airbags;
	public String getEngine(){
		return this.engine;
	}
	public int getwheels(){
		return this.wheels;
	}
	public int airbags(){
		return this.airbags;
	}
	private Veichle(VeichleBuilder builder){
		this.engine= builder.engine;
		this.airbags=builder.airbags;
		this.wheels=builder.wheels;
	
	}
	public static class VeichleBuilder{
		private String engine;
		private int wheels;
	private int airbags;
	public VeichleBuilder(String engine, int wheels){
	this.engine=engine;
	this.wheels=wheels;
	
	}
	public VeichleBuilder setAirbags(int airbags){
		this.airbags=airbags;
		return this;
	}
	public Veichle build(){
	return new Veichle(this);
	}
	
	}
}
public class BuilderPatternExample{
public static void main(String args[]){
	Veichle car = new Veichle.VeichleBuilder("1500cc",4).setAirbags(2).build();
	Veichle bike = new Veichle.VeichleBuilder("100cc",2).build();
	System.out.println(car.getEngine());
	System.out.println(bike.getEngine());
	System.out.println(car.airbags());
	
}
}