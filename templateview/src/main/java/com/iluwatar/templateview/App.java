/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.templateview;

import lombok.extern.slf4j.Slf4j;

/**
 * Template View defines a consistent layout for rendering views, delegating dynamic content
 * rendering to subclasses.
 *
 * <p>In this example, the {@link TemplateView} class provides the skeleton for rendering views
 * with a header, dynamic content, and a footer. Subclasses {@link HomePageView} and
 * {@link ContactPageView} define the specific dynamic content for their respective views.
 *
 * <p>The {@link App} class demonstrates the usage of the Template View Pattern by rendering
 * instances of {@link HomePageView} and {@link ContactPageView}.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // Create and render the HomePageView
    TemplateView homePage = new HomePageView();
    LOGGER.info("Rendering HomePage:");
    homePage.render();

    // Create and render the ContactPageView
    TemplateView contactPage = new ContactPageView();
    LOGGER.info("\nRendering ContactPage:");
    contactPage.render();
  }
}
