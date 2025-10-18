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

package com.iluwatar.serverfragment.composition;

import lombok.extern.slf4j.Slf4j;

/**
 * Page composer responsible for assembling fragments into complete web pages.
 *
 * <p>This class handles the final composition step where individual fragments from different
 * microservices are combined into a cohesive HTML page. It ensures proper HTML structure and
 * manages the composition process.
 */
@Slf4j
public class PageComposer {

  /**
   * Composes a complete HTML page from individual fragments.
   *
   * <p>This method takes fragments from different microservices and assembles them into a
   * well-formed HTML document with proper structure and styling.
   *
   * @param headerContent HTML content for the header section
   * @param mainContent HTML content for the main content area
   * @param footerContent HTML content for the footer section
   * @return complete HTML page as a string
   */
  public String composePage(String headerContent, String mainContent, String footerContent) {
    LOGGER.info("PageComposer: Assembling page from {} fragments", 3);

    var startTime = System.currentTimeMillis();

    // Validate fragments before composition
    validateFragment("header", headerContent);
    validateFragment("main content", mainContent);
    validateFragment("footer", footerContent);

    // Compose the complete page
    var completePage = buildHtmlPage(headerContent, mainContent, footerContent);

    var compositionTime = System.currentTimeMillis() - startTime;
    LOGGER.debug(
        "PageComposer: Page composition completed in {}ms, total size: {} characters",
        compositionTime,
        completePage.length());

    return completePage;
  }

  /**
   * Builds the complete HTML page structure.
   *
   * @param headerContent the header fragment
   * @param mainContent the main content fragment
   * @param footerContent the footer fragment
   * @return complete HTML page
   */
  private String buildHtmlPage(String headerContent, String mainContent, String footerContent) {
    return String.format(
        """
        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Server-Side Fragment Composition Demo</title>
          <style>
            body { font-family: Arial, sans-serif; margin: 0; padding: 0; line-height: 1.6; }
            .site-header { background: #333; color: white; padding: 1rem; }
            .header-content { display: flex; justify-content: space-between; align-items: center; }
            .site-title { margin: 0; }
            .main-navigation ul { list-style: none; display: flex; gap: 1rem; margin: 0; padding: 0; }
            .main-navigation a { color: white; text-decoration: none; }
            .main-navigation a:hover { text-decoration: underline; }
            .user-info { font-size: 0.9em; }
            .main-content { min-height: 60vh; padding: 2rem; }
            .content-wrapper { max-width: 1200px; margin: 0 auto; }
            .site-footer { background: #f4f4f4; padding: 2rem; border-top: 1px solid #ddd; }
            .footer-content { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 2rem; max-width: 1200px; margin: 0 auto; }
            .footer-section h4 { margin-top: 0; }
            .footer-section ul { list-style: none; padding: 0; }
            .footer-section a { color: #666; text-decoration: none; }
            .footer-section a:hover { color: #333; }
            .footer-bottom { text-align: center; margin-top: 2rem; padding-top: 1rem; border-top: 1px solid #ddd; color: #666; }
          </style>
        </head>
        <body>
          <!-- Generated by Header Service -->
          %s

          <!-- Generated by Content Service -->
          %s

          <!-- Generated by Footer Service -->
          %s

          <!-- Composition metadata -->
          <script>
            console.log('Page composed by Server-Side Fragment Composition pattern');
            console.log('Composition timestamp: %d');
          </script>
        </body>
        </html>
        """,
        headerContent, mainContent, footerContent, System.currentTimeMillis());
  }

  /**
   * Validates that a fragment is not null or empty.
   *
   * @param fragmentName name of the fragment for logging
   * @param fragmentContent the fragment content to validate
   * @throws IllegalArgumentException if fragment is invalid
   */
  private void validateFragment(String fragmentName, String fragmentContent) {
    if (fragmentContent == null || fragmentContent.trim().isEmpty()) {
      var errorMessage =
          String.format("Invalid %s fragment: content is null or empty", fragmentName);
      LOGGER.error("PageComposer: {}", errorMessage);
      throw new IllegalArgumentException(errorMessage);
    }

    LOGGER.debug(
        "PageComposer: {} fragment validated successfully ({} characters)",
        fragmentName,
        fragmentContent.length());
  }

  /**
   * Composes a page with custom CSS styling.
   *
   * <p>This method allows for custom styling to be applied during composition, useful for different
   * themes or page-specific styles.
   *
   * @param headerContent HTML content for the header section
   * @param mainContent HTML content for the main content area
   * @param footerContent HTML content for the footer section
   * @param customCss additional CSS to include
   * @return complete HTML page with custom styling
   */
  public String composePageWithCustomStyling(
      String headerContent, String mainContent, String footerContent, String customCss) {
    LOGGER.info("PageComposer: Assembling page with custom styling");

    var basePage = composePage(headerContent, mainContent, footerContent);

    // Insert custom CSS before closing </head> tag
    var customStyledPage =
        basePage.replace("</head>", String.format("<style>%s</style>\n  </head>", customCss));

    LOGGER.debug("PageComposer: Custom styling applied successfully");

    return customStyledPage;
  }
}
