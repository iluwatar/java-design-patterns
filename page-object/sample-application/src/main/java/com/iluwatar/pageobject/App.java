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

package com.iluwatar.pageobject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object pattern wraps an UI component with an application specific API allowing you to
 * manipulate the UI elements without having to dig around with the underlying UI technology used.
 * This is especially useful for testing as it means your tests will be less brittle. Your tests can
 * concentrate on the actual test cases where as the manipulation of the UI can be left to the
 * internals of the page object itself.
 *
 * <p>Due to this reason, it has become very popular within the test automation community. In
 * particular, it is very common in that the page object is used to represent the html pages of a
 * web application that is under test. This web application is referred to as AUT (Application Under
 * Test). A web browser automation tool/framework like Selenium for instance, is then used to drive
 * the automating of the browser navigation and user actions journeys through this web application.
 * Your test class would therefore only be responsible for particular test cases and page object
 * would be used by the test class for UI manipulation required for the tests.
 *
 * <p>In this implementation rather than using Selenium, the HtmlUnit library is used as a
 * replacement to represent the specific html elements and to drive the browser. The purpose of this
 * example is just to provide a simple version that showcase the intentions of this pattern and how
 * this pattern is used in order to understand it.
 */
public final class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private App() {
  }

  /**
   * Application entry point
   *
   * <p>The application under development is a web application. Normally you would probably have a
   * backend that is probably implemented in an object-oriented language (e.g. Java) that serves the
   * frontend which comprises of a series of HTML, CSS, JS etc...
   *
   * <p>For illustrations purposes only, a very simple static html app is used here. This main
   * method just fires up this simple web app in a default browser.
   *
   * @param args arguments
   */
  public static void main(String[] args) {

    try {
      File applicationFile =
          new File(App.class.getClassLoader().getResource("sample-ui/login.html").getPath());

      // should work for unix like OS (mac, unix etc...)
      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(applicationFile);

      } else {
        // java Desktop not supported - above unlikely to work for Windows so try instead...
        Runtime.getRuntime().exec("cmd.exe start " + applicationFile);
      }

    } catch (IOException ex) {
      LOGGER.error("An error occured.", ex);
    }

  }
}
