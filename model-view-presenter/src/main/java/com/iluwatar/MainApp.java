package com.iluwatar;

/**
 * 
 * The Model-View-Presenter(MVP) architectural pattern, helps us achieve what is
 * called "The separation of concerns" principle. This is accomplished
 * by separating the application's logic(Model), GUIs(View), and finally 
 * the way that the user's actions update the application's logic(Presenter).
 * 
 * In the following example, The FileLoader class represents the app's logic,
 * the FileSelectorJFrame is the GUI and the FileSelectorPresenter is
 * responsible to respond to users' actions.
 * 
 * Finally, please notice the wiring between the Presenter and the View
 * and between the Presenter and the Model.
 * 
 */
public class MainApp {

	public static void main(String[] args) {
		FileLoader loader = new FileLoader();
		FileSelectorJFrame jFrame = new FileSelectorJFrame();
		FileSelectorPresenter presenter = new FileSelectorPresenter(jFrame);
		presenter.setLoader(loader);
		presenter.start();
	}
}
