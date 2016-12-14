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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This test case is responsible for testing our application by taking advantage of the
 * Model-View-Controller architectural pattern.
 */
public class FileSelectorPresenterTest {

  /**
   * The Presenter component.
   */
  private FileSelectorPresenter presenter;

  /**
   * The View component, implemented this time as a Stub!!!
   */
  private FileSelectorStub stub;

  /**
   * The Model component.
   */
  private FileLoader loader;

  /**
   * Initializes the components of the test case.
   */
  @Before
  public void setUp() {
    this.stub = new FileSelectorStub();
    this.loader = new FileLoader();
    presenter = new FileSelectorPresenter(this.stub);
    presenter.setLoader(loader);
  }

  /**
   * Tests if the Presenter was successfully connected with the View.
   */
  @Test
  public void wiring() {
    presenter.start();

    assertNotNull(stub.getPresenter());
    assertTrue(stub.isOpened());
  }

  /**
   * Tests if the name of the file changes.
   */
  @Test
  public void updateFileNameToLoader() {
    String expectedFile = "Stamatis";
    stub.setFileName(expectedFile);

    presenter.start();
    presenter.fileNameChanged();

    assertEquals(expectedFile, loader.getFileName());
  }

  /**
   * Tests if we receive a confirmation when we attempt to open a file that it's name is null or an
   * empty string.
   */
  @Test
  public void fileConfirmationWhenNameIsNull() {
    stub.setFileName(null);

    presenter.start();
    presenter.fileNameChanged();
    presenter.confirmed();

    assertFalse(loader.isLoaded());
    assertEquals(1, stub.getMessagesSent());
  }

  /**
   * Tests if we receive a confirmation when we attempt to open a file that it doesn't exist.
   */
  @Test
  public void fileConfirmationWhenFileDoesNotExist() {
    stub.setFileName("RandomName.txt");

    presenter.start();
    presenter.fileNameChanged();
    presenter.confirmed();

    assertFalse(loader.isLoaded());
    assertEquals(1, stub.getMessagesSent());
  }

  /**
   * Tests if we can open the file, when it exists.
   */
  @Test
  public void fileConfirmationWhenFileExists() {
    stub.setFileName("etc/data/test.txt");
    presenter.start();
    presenter.fileNameChanged();
    presenter.confirmed();

    assertTrue(loader.isLoaded());
    assertTrue(stub.dataDisplayed());
  }

  /**
   * Tests if the view closes after cancellation.
   */
  @Test
  public void cancellation() {
    presenter.start();
    presenter.cancelled();

    assertFalse(stub.isOpened());
  }
}
