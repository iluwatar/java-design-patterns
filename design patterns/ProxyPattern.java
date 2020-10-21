public class ProxyPattern{
	public static void main(String args[])
	{
		Image image= new ProxyImage("test_img.jpg");
		image.display();
		image.display();
	}
}