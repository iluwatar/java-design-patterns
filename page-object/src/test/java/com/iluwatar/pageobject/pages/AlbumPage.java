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

package com.iluwatar.pageobject.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlNumberInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;

/**
 * Page Object encapsulating the Album Page (album-page.html)
 */
public class AlbumPage extends Page {

  private static final String ALBUM_PAGE_HTML_FILE = "album-page.html";
  private static final String PAGE_URL = "file:" + AUT_PATH + ALBUM_PAGE_HTML_FILE;

  private HtmlPage page;


  /**
   * Constructor
   */
  public AlbumPage(WebClient webClient) {
    super(webClient);
  }


  /**
   * Navigates to the album page
   *
   * @return {@link AlbumPage}
   */
  public AlbumPage navigateToPage() {
    try {
      page = this.webClient.getPage(PAGE_URL);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAt() {
    return "Album Page".equals(page.getTitleText());
  }


  /**
   * Sets the album title input text field
   *
   * @param albumTitle the new album title value to set
   * @return {@link AlbumPage}
   */
  public AlbumPage changeAlbumTitle(String albumTitle) {
    var albumTitleInputTextField = (HtmlTextInput) page.getElementById("albumTitle");
    albumTitleInputTextField.setText(albumTitle);
    return this;
  }


  /**
   * Sets the artist input text field
   *
   * @param artist the new artist value to set
   * @return {@link AlbumPage}
   */
  public AlbumPage changeArtist(String artist) {
    var artistInputTextField = (HtmlTextInput) page.getElementById("albumArtist");
    artistInputTextField.setText(artist);
    return this;
  }


  /**
   * Selects the select's option value based on the year value given
   *
   * @param year the new year value to set
   * @return {@link AlbumPage}
   */
  public AlbumPage changeAlbumYear(int year) {
    var albumYearSelectOption = (HtmlSelect) page.getElementById("albumYear");
    var yearOption = albumYearSelectOption.getOptionByValue(Integer.toString(year));
    albumYearSelectOption.setSelectedAttribute(yearOption, true);
    return this;
  }


  /**
   * Sets the album rating input text field
   *
   * @param albumRating the new album rating value to set
   * @return {@link AlbumPage}
   */
  public AlbumPage changeAlbumRating(String albumRating) {
    var albumRatingInputTextField = (HtmlTextInput) page.getElementById("albumRating");
    albumRatingInputTextField.setText(albumRating);
    return this;
  }

  /**
   * Sets the number of songs number input field
   *
   * @param numberOfSongs the new number of songs value to be set
   * @return {@link AlbumPage}
   */
  public AlbumPage changeNumberOfSongs(int numberOfSongs) {
    var numberOfSongsNumberField = (HtmlNumberInput) page.getElementById("numberOfSongs");
    numberOfSongsNumberField.setText(Integer.toString(numberOfSongs));
    return this;
  }


  /**
   * Cancel changes made by clicking the cancel button
   *
   * @return {@link AlbumListPage}
   */
  public AlbumListPage cancelChanges() {
    var cancelButton = (HtmlSubmitInput) page.getElementById("cancelButton");
    try {
      cancelButton.click();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new AlbumListPage(webClient);
  }


  /**
   * Saves changes made by clicking the save button
   *
   * @return {@link AlbumPage}
   */
  public AlbumPage saveChanges() {
    var saveButton = (HtmlSubmitInput) page.getElementById("saveButton");
    try {
      saveButton.click();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return this;
  }

}
