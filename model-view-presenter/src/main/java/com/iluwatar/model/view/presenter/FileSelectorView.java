/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.model.view.presenter;

import java.io.Serializable;

/**
 * This interface represents the View component in the Model-View-Presenter pattern. It can be
 * implemented by either the GUI components, or by the Stub.
 */
public interface FileSelectorView extends Serializable {

  /**
   * Opens the view.
   */
  void open();

  /**
   * Closes the view.
   */
  void close();

  /**
   * Returns true if view is opened.
   *
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
   * Gets presenter component.
   *
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
   * Gets the name of file.
   *
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
