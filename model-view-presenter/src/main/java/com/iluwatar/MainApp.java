
public class MainApp {
	
	public static void main(String[] args) {
		FileLoader loader = new FileLoader();
		FileSelectorJFrame jFrame = new FileSelectorJFrame();
		FileSelectorPresenter presenter = new FileSelectorPresenter(jFrame);
		presenter.setLoader(loader);
		presenter.start();
	}
}
