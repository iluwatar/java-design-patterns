package com.iluwatar.model.view.presenter;

/**
 * Every instance of this class represents the Presenter component in the
 * Model-View-Presenter architectural pattern.
 * <p>
 * It is responsible for reacting to the user's actions and update the View
 * component.
 */
public class FileSelectorPresenter {

	/**
	 * The View component that the presenter interacts with.
	 */
	private FileSelectorView view;

	/**
	 * The Model component that the presenter interacts with.
	 */
	private FileLoader loader;

	/**
	 * Constructor
	 * @param view The view component that the presenter will interact with.
	 */
	public FileSelectorPresenter(FileSelectorView view) {
		this.view = view;
	}

	/**
	 * Sets the {@link FileLoader} object, to the value given as parameter.
	 * @param loader The new {@link FileLoader} object(the Model component).
	 */
	public void setLoader(FileLoader loader) {
		this.loader = loader;
	}

	/**
	 * Starts the presenter.
	 */
	public void start() {
		view.setPresenter(this);
		view.open();
	}

	/**
	 * An "event" that fires when the name of the file to be loaded changes.
	 */
	public void fileNameChanged() {
		loader.setFileName(view.getFileName());
	}

	public void confirmed() {
		if (loader.getFileName() == null || loader.getFileName().equals("")) {
			view.showMessage("Please give the name of the file first!");
			return;
		}

		if (loader.fileExists()) {
			String data = loader.loadData();
			view.displayData(data);
		}

		else {
			view.showMessage("The file specified does not exist.");
		}
	}

	/**
	 * Cancels the file loading process.
	 */
	public void cancelled() {
		view.close();
	}
}
