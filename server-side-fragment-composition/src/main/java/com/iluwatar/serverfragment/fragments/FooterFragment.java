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

package com.iluwatar.serverfragment.fragments;

import com.iluwatar.serverfragment.types.PageContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

/**
 * Footer fragment implementation for Server-Side Page Fragment Composition.
 *
 * <p>This fragment is responsible for rendering the footer section of web pages, typically
 * containing copyright information, links, and dynamic footer content.
 */
@Slf4j
public class FooterFragment implements Fragment {

  @Override
  public String render(PageContext context) {
    LOGGER.info("Rendering footer fragment for page: {}", context.getPageId());

    var currentTime =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    var currentYear = LocalDateTime.now().getYear();

    return String.format(
        """
        <footer class="site-footer">
          <div class="footer-content">
            <div class="footer-section">
              <h4>Design Patterns</h4>
              <ul>
                <li><a href="/patterns/architectural">Architectural Patterns</a></li>
                <li><a href="/patterns/behavioral">Behavioral Patterns</a></li>
                <li><a href="/patterns/creational">Creational Patterns</a></li>
                <li><a href="/patterns/structural">Structural Patterns</a></li>
              </ul>
            </div>
            <div class="footer-section">
              <h4>Resources</h4>
              <ul>
                <li><a href="/documentation">Documentation</a></li>
                <li><a href="/examples">Examples</a></li>
                <li><a href="/best-practices">Best Practices</a></li>
                <li><a href="/community">Community</a></li>
              </ul>
            </div>
            <div class="footer-section">
              <h4>Connect</h4>
              <ul>
                <li><a href="#github">GitHub</a></li>
                <li><a href="#twitter">Twitter</a></li>
                <li><a href="#linkedin">LinkedIn</a></li>
                <li><a href="#blog">Blog</a></li>
              </ul>
            </div>
          </div>
          <div class="footer-bottom">
            <p>&copy; %d Server-Side Fragment Composition Demo. All rights reserved.</p>
            <p>Page generated at: %s | Rendered by Footer Service</p>
          </div>
        </footer>
        """,
        currentYear, currentTime);
  }

  @Override
  public String getType() {
    return "footer";
  }

  @Override
  public int getPriority() {
    return 3; // Lowest priority - rendered last
  }

  @Override
  public boolean isCacheable() {
    return false; // Footer contains timestamp, so shouldn't be cached
  }
}
