/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppala
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

package com.iluwatar.pageobject

// ABOUTME: Page Object encapsulating the Album Page (album-page.html).
// ABOUTME: Provides methods to change album fields and save/cancel changes.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlNumberInput
import org.htmlunit.html.HtmlPage
import org.htmlunit.html.HtmlSelect
import org.htmlunit.html.HtmlSubmitInput
import org.htmlunit.html.HtmlTextInput
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Page Object encapsulating the Album Page (album-page.html).
 */
class AlbumPage(webClient: WebClient) : Page(webClient) {

    private lateinit var page: HtmlPage

    /**
     * Navigates to the album page.
     *
     * @return this [AlbumPage]
     */
    fun navigateToPage(): AlbumPage {
        try {
            page = webClient.getPage("file:${AUT_PATH}$ALBUM_PAGE_HTML_FILE")
        } catch (e: IOException) {
            logger.error(e) { "An error occurred on navigateToPage." }
        }
        return this
    }

    override fun isAt(): Boolean = "Album Page" == page.titleText

    /**
     * Sets the album title input text field.
     *
     * @param albumTitle the album title value to set
     * @return this [AlbumPage]
     */
    fun changeAlbumTitle(albumTitle: String): AlbumPage {
        val albumTitleInputTextField = page.getElementById("albumTitle") as HtmlTextInput
        albumTitleInputTextField.text = albumTitle
        return this
    }

    /**
     * Sets the artist input text field.
     *
     * @param artist the artist value to set
     * @return this [AlbumPage]
     */
    fun changeArtist(artist: String): AlbumPage {
        val artistInputTextField = page.getElementById("albumArtist") as HtmlTextInput
        artistInputTextField.text = artist
        return this
    }

    /**
     * Selects the select's option value based on the year value given.
     *
     * @param year the year value to set
     * @return this [AlbumPage]
     */
    fun changeAlbumYear(year: Int): AlbumPage {
        val albumYearSelectOption = page.getElementById("albumYear") as HtmlSelect
        val yearOption = albumYearSelectOption.getOptionByValue(year.toString())
        albumYearSelectOption.setSelectedAttribute<HtmlPage>(yearOption, true)
        return this
    }

    /**
     * Sets the album rating input text field.
     *
     * @param albumRating the album rating value to set
     * @return this [AlbumPage]
     */
    fun changeAlbumRating(albumRating: String): AlbumPage {
        val albumRatingInputTextField = page.getElementById("albumRating") as HtmlTextInput
        albumRatingInputTextField.text = albumRating
        return this
    }

    /**
     * Sets the number of songs number input field.
     *
     * @param numberOfSongs the number of songs value to be set
     * @return this [AlbumPage]
     */
    fun changeNumberOfSongs(numberOfSongs: Int): AlbumPage {
        val numberOfSongsNumberField = page.getElementById("numberOfSongs") as HtmlNumberInput
        numberOfSongsNumberField.text = numberOfSongs.toString()
        return this
    }

    /**
     * Cancel changes made by clicking the cancel button.
     *
     * @return [AlbumListPage]
     */
    fun cancelChanges(): AlbumListPage {
        val cancelButton = page.getElementById("cancelButton") as HtmlSubmitInput
        try {
            cancelButton.click<HtmlPage>()
        } catch (e: IOException) {
            logger.error(e) { "An error occurred on cancelChanges." }
        }
        return AlbumListPage(webClient)
    }

    /**
     * Saves changes made by clicking the save button.
     *
     * @return this [AlbumPage]
     */
    fun saveChanges(): AlbumPage {
        val saveButton = page.getElementById("saveButton") as HtmlSubmitInput
        try {
            saveButton.click<HtmlPage>()
        } catch (e: IOException) {
            logger.error(e) { "An error occurred on saveChanges." }
        }
        return this
    }

    companion object {
        private const val ALBUM_PAGE_HTML_FILE = "album-page.html"
    }
}
