/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

/**
 * Every instance of this class represents the Stub component in the Model-View-Presenter
 * architectural pattern.
 * <p>
 * The stub implements the View interface and it is useful when we want the test the reaction to
 * user events, such as mouse clicks.
 * <p>
 * Since we can not test the GUI directly, the MVP pattern provides this functionality through the
 * View's dummy implementation, the Stub.
 */
public class FileSelectorStub implements FileSelectorView {

  /**
   * Indicates whether or not the view is opened.
   */
  private boolean opened;

  /**
   * The presenter Component.
   */
  private FileSelectorPresenter presenter;

  /**
   * The current name of the file.
   */
  private String name;

  /**
   * Indicates the number of messages that were "displayed" to the user.
   */
  private int numOfMessageSent;

  /**
   * Indicates if the data of the file where displayed or not.
   */
  private boolean dataDisplayed;

  /**
   * Constructor
   */
  public FileSelectorStub() {
    this.opened = false;
    this.presenter = null;
    this.name = "";
    this.numOfMessageSent = 0;
    this.dataDisplayed = false;
  }

  @Override
  public void open() {
    this.opened = true;
  }

  @Override
  public void setPresenter(FileSelectorPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public boolean isOpened() {
    return this.opened;
  }

  @Override
  public FileSelectorPresenter getPresenter() {
    return this.presenter;
  }

  @Override
  public String getFileName() {
    return this.name;
  }

  @Override
  public void setFileName(String name) {
    this.name = name;
  }

  @Override
  public void showMessage(String message) {
    this.numOfMessageSent++;
  }

  @Override
  public void close() {
    this.opened = false;
  }

  @Override
  public void displayData(String data) {
    this.dataDisplayed = true;
  }

  /**
   * Returns the number of messages that were displayed to the user.
   */
  public int getMessagesSent() {
    return this.numOfMessageSent;
  }

  /**
   * @return True if the data where displayed, false otherwise.
   */
  public boolean dataDisplayed() {
    return this.dataDisplayed;
  }
}
