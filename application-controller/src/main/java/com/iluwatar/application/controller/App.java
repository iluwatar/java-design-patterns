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

package com.iluwatar.application.controller;

/**
 * Application-Controller is a pattern that is commonly used when implementing user interfaces.
 * It centralizes action and view management in an application controller class.
 *
 * <p>In this example we have an application controller ({@link ApplicationController}) that
 * coordinates user input and the display of different pages. {@link SiteMapper} maintains a map 
 * that links characters to the {@link Target} objects: {@link Home}, {@link About} and 
 * {@link Contact}. The ApplicationController's method, "handler", retrieves references to the 
 * target objects from the map and invokes the targets' "invoke" methods.
 */

import static java.lang.Thread.sleep;

/**
 * The sample application.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    ApplicationController controller = new ApplicationController();

    try {

      // Navigate to the Home page
      controller.handler(sanitizeInput('h'));

      // Navigate to the About Us Page
      print("(User presses the {A} key)");
      controller.handler(sanitizeInput('a'));

      // Navigate to the Contact Us Page
      print("(User presses the {C} key)");
      controller.handler(sanitizeInput('c'));

      //Exit the application
      print("(User presses the {X} key)");
      controller.handler(sanitizeInput('x'));
    } catch (NullPointerException e) {
      Target.clearScreen();
    }
  }

  /**
   * Function for sanitizing keyboard input to ensure all characters are uppercase.
   *
   * @param input keyboard key that was pressed
   * @return lowercase version of input character
   */
  public static String sanitizeInput(char input) {
    return Character.toString(Character.toUpperCase(input));
  }

  /**
   * Display the input text in the console.
   * @param text the text to be displayed
   */
  public static void print(String text) {
    pause();
    System.out.println(text);
    pause();
  }

  /**
   * Delay execution of the current thread.
   *
   */
  public static void pause() {
    final long duration = 3000;

    try {
      sleep(duration);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
