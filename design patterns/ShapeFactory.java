public ShapeFactory extends AbstarctFactory{
	@Override
	public Shape getShape(String shapeType){
		if(shapeType.equalsIgnoreCase("Rectangle")){
			return new Rectangle();
		}
		else if(shapeType.equalsIgnoreCase("Square")){
			return new Square();
		}
		return null;
	}
}