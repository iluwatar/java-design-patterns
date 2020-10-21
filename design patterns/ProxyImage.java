public class ProxyImage implements Image{
	private String fileName;
	private RealImage realImage;
	public ProxyImage(String fileName){
		this.fileName=fileName;		
	}
	@Override
	public void display(){
		if(realImage==null){
			realImage= new RealImage(fileName);
		}
		realImage.display();
		
	}
}