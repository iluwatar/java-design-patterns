/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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
 * Every instance of this class represents the Presenter component in the Model-View-Presenter
 * architectural pattern.
 *
 * <p>It is responsible for reacting to the user's actions and update the View component.
 */
public class FileSelectorPresenter implements Serializable {

  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 1210314339075855074L;

  /**
   * The View component that the presenter interacts with.
   */
  private FileSelectorView view;

  /**
   * The Model component that the presenter interacts with.
   */
  private FileLoader loader;

  /**
   * Constructor.
   *
   * @param view The view component that the presenter will interact with.
   */
  public FileSelectorPresenter(FileSelectorView view) {
    this.view = view;
  }

  /**
   * Sets the {@link FileLoader} object, to the value given as parameter.
   *
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

  /**
   * Ok button handler.
   */
  public void confirmed() {
    if (loader.getFileName() == null || loader.getFileName().equals("")) {
      view.showMessage("Please give the name of the file first!");
      return;
    }

    if (loader.fileExists()) {
      String data = loader.loadData();
      view.displayData(data);
    } else {
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
