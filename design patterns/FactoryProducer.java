public class FactoryProducer{
	public static AbstarctFactory getFactory(boolean rounded){
		if(rounded)
			return new RoundedShapeFactory;
		else
			return new ShapeFactory;
	}
}