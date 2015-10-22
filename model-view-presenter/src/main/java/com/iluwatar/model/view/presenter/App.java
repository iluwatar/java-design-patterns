package com.iluwatar.model.view.presenter;

/**
 * 
 * The Model-View-Presenter(MVP) architectural pattern, helps us achieve what is
 * called "The separation of concerns" principle. This is accomplished
 * by separating the application's logic (Model), GUIs (View), and finally 
 * the way that the user's actions update the application's logic (Presenter).
 * <p>
 * In the following example, The {@link FileLoader} class represents the app's logic,
 * the {@link FileSelectorJFrame} is the GUI and the {@link FileSelectorPresenter} is
 * responsible to respond to users' actions.
 * <p>
 * Finally, please notice the wiring between the Presenter and the View
 * and between the Presenter and the Model.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		FileLoader loader = new FileLoader();
		FileSelectorJFrame jFrame = new FileSelectorJFrame();
		FileSelectorPresenter presenter = new FileSelectorPresenter(jFrame);
		presenter.setLoader(loader);
		presenter.start();
	}
}
