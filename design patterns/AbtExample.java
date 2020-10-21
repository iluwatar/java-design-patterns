public class AbtExample{
	public static void main(String args[])
	{
		AbstarctFactory shapeFactory= FactoryProducer.getFactory(false);
		Shape s1= shapeFactory.getShape("Rectangle");
		Shape s1= shapeFactory.getShape("Square");
		AbstarctFactory shapeFactory2= FactoryProducer.getFactory(true);
		Shape s3= shapeFactory.getShape("Rectangle");
		Shape s4= shapeFactory.getShape("Square");
	}
}