package com.iluwatar.model.view.presenter;

/**
 * This interface represents the View component in the Model-View-Presenter pattern. It can be
 * implemented by either the GUI components, or by the Stub.
 */
public interface FileSelectorView {

  /**
   * Opens the view.
   */
  void open();

  /**
   * Closes the view.
   */
  void close();

  /**
   * @return True, if the view is opened, false otherwise.
   */
  boolean isOpened();

  /**
   * Sets the presenter component, to the one given as parameter.
   * 
   * @param presenter The new presenter component.
   */
  void setPresenter(FileSelectorPresenter presenter);

  /**
   * @return The presenter Component.
   */
  FileSelectorPresenter getPresenter();

  /**
   * Sets the file's name, to the value given as parameter.
   * 
   * @param name The new name of the file.
   */
  void setFileName(String name);

  /**
   * @return The name of the file.
   */
  String getFileName();

  /**
   * Displays a message to the users.
   * 
   * @param message The message to be displayed.
   */
  void showMessage(String message);

  /**
   * Displays the data to the view.
   * 
   * @param data The data to be written.
   */
  void displayData(String data);
}
