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

// ABOUTME: Page Object encapsulating the Album List page (album-list.html).
// ABOUTME: Provides navigation and album selection via XPath-based link lookup.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.htmlunit.WebClient
import org.htmlunit.html.HtmlAnchor
import org.htmlunit.html.HtmlPage
import java.io.IOException

private val logger = KotlinLogging.logger {}

/**
 * Page Object encapsulating the Album List page (album-list.html).
 */
class AlbumListPage(webClient: WebClient) : Page(webClient) {

    private lateinit var page: HtmlPage

    /**
     * Navigates to the Album List Page.
     *
     * @return this [AlbumListPage]
     */
    fun navigateToPage(): AlbumListPage {
        try {
            page = webClient.getPage("file:${AUT_PATH}$ALBUM_LIST_HTML_FILE")
        } catch (e: IOException) {
            logger.error(e) { "An error occurred on navigateToPage." }
        }
        return this
    }

    override fun isAt(): Boolean = "Album List" == page.titleText

    /**
     * Selects an album by the given album title.
     *
     * @param albumTitle the title of the album to click
     * @return the album page
     */
    fun selectAlbum(albumTitle: String): AlbumPage {
        // uses XPath to find list of html anchor tags with the class album in it
        val albumLinks: List<HtmlAnchor> = page.getByXPath("//tr[@class='album']//a")
        for (anchor in albumLinks) {
            if (anchor.textContent == albumTitle) {
                try {
                    anchor.click<HtmlPage>()
                    return AlbumPage(webClient)
                } catch (e: IOException) {
                    logger.error(e) { "An error occurred on selectAlbum" }
                }
            }
        }
        throw IllegalArgumentException("No links with the album title: $albumTitle")
    }

    companion object {
        private const val ALBUM_LIST_HTML_FILE = "album-list.html"
    }
}
