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
package com.iluwatar.pageobject;

import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlPage;

/** Page Object encapsulating the Album List page (album-list.html) */
@Slf4j
public class AlbumListPage extends Page {
  private static final String ALBUM_LIST_HTML_FILE = "album-list.html";
  private static final String PAGE_URL = "file:" + AUT_PATH + ALBUM_LIST_HTML_FILE;

  private HtmlPage page;

  /** Constructor. */
  public AlbumListPage(WebClient webClient) {
    super(webClient);
  }

  /**
   * Navigates to the Album List Page.
   *
   * @return {@link AlbumListPage}
   */
  public AlbumListPage navigateToPage() {
    try {
      page = this.webClient.getPage(PAGE_URL);
    } catch (IOException e) {
      LOGGER.error("An error occurred on navigateToPage.", e);
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isAt() {
    return "Album List".equals(page.getTitleText());
  }

  /**
   * Selects an album by the given album title.
   *
   * @param albumTitle the title of the album to click
   * @return the album page
   */
  public AlbumPage selectAlbum(String albumTitle) {
    // uses XPath to find list of html anchor tags with the class album in it
    var albumLinks = (List<Object>) page.getByXPath("//tr[@class='album']//a");
    for (var anchor : albumLinks) {
      if (((HtmlAnchor) anchor).getTextContent().equals(albumTitle)) {
        try {
          ((HtmlAnchor) anchor).click();
          return new AlbumPage(webClient);
        } catch (IOException e) {
          LOGGER.error("An error occurred on selectAlbum", e);
        }
      }
    }
    throw new IllegalArgumentException("No links with the album title: " + albumTitle);
  }
}
