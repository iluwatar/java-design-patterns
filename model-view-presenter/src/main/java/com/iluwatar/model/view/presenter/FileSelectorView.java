package com.iluwatar.model.view.presenter;

/**
 * This interface represents the View component in the Model-View-Presenter
 * pattern. It can be implemented by either the GUI components, or by the Stub.
 */
public interface FileSelectorView {

	/**
	 * Opens the view.
	 */
	public void open();

	/**
	 * Closes the view.
	 */
	public void close();

	/**
	 * @return True, if the view is opened, false otherwise.
	 */
	public boolean isOpened();

	/**
	 * Sets the presenter component, to the one given as parameter.
	 * @param presenter The new presenter component.
	 */
	public void setPresenter(FileSelectorPresenter presenter);

	/**
	 * @return The presenter Component.
	 */
	public FileSelectorPresenter getPresenter();

	/**
	 * Sets the file's name, to the value given as parameter.
	 * @param name The new name of the file.
	 */
	public void setFileName(String name);

	/**
	 * @return The name of the file.
	 */
	public String getFileName();

	/**
	 * Displays a message to the users.
	 * @param message The message to be displayed.
	 */
	public void showMessage(String message);

	/**
	 * Displays the data to the view.
	 * @param data The data to be written.
	 */
	public void displayData(String data);
}
