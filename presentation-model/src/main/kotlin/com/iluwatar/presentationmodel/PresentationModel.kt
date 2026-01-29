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

// ABOUTME: The presentation model that mediates between the view and album data.
// ABOUTME: Handles selection, data access, and modification of album properties.
package com.iluwatar.presentationmodel

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The class between view and albums, it is used to control the data.
 *
 * @param data the data of all albums that will be shown
 */
class PresentationModel(private val data: DisplayedAlbums) {
    /** the no of selected album. */
    private var selectedAlbumNumber: Int = 1

    /** the selected album. */
    private var selectedAlbum: Album = data.albums[0]

    /**
     * Changes the value of selectedAlbumNumber.
     *
     * @param albumNumber the number of album which is shown on the view
     */
    fun setSelectedAlbumNumber(albumNumber: Int) {
        logger.info { "Change select number from $selectedAlbumNumber to $albumNumber" }
        selectedAlbumNumber = albumNumber
        selectedAlbum = data.albums[selectedAlbumNumber - 1]
    }

    /**
     * Get the title of selected album.
     *
     * @return the title of selected album
     */
    fun getTitle(): String = selectedAlbum.title

    /**
     * Set the title of selected album.
     *
     * @param value the title which user want to use
     */
    fun setTitle(value: String) {
        logger.info { "Change album title from ${selectedAlbum.title} to $value" }
        selectedAlbum.title = value
    }

    /**
     * Get the artist of selected album.
     *
     * @return the artist of selected album
     */
    fun getArtist(): String = selectedAlbum.artist

    /**
     * Set the name of artist.
     *
     * @param value the name want artist to be
     */
    fun setArtist(value: String) {
        logger.info { "Change album artist from ${selectedAlbum.artist} to $value" }
        selectedAlbum.artist = value
    }

    /**
     * Gets a boolean value which represents whether the album is classical.
     *
     * @return is the album classical
     */
    fun getIsClassical(): Boolean = selectedAlbum.isClassical

    /**
     * Set the isClassical of album.
     *
     * @param value is the album classical
     */
    fun setIsClassical(value: Boolean) {
        logger.info { "Change album isClassical from ${selectedAlbum.isClassical} to $value" }
        selectedAlbum.isClassical = value
    }

    /**
     * Get the composer of the selected album.
     *
     * @return the composer if classical, empty string otherwise
     */
    fun getComposer(): String = if (selectedAlbum.isClassical) selectedAlbum.composer else ""

    /**
     * Sets the name of composer when the album is classical.
     *
     * @param value the name of composer
     */
    fun setComposer(value: String) {
        if (selectedAlbum.isClassical) {
            logger.info { "Change album composer from ${selectedAlbum.composer} to $value" }
            selectedAlbum.composer = value
        } else {
            logger.info { "Composer can not be changed" }
        }
    }

    /**
     * Gets a list of albums.
     *
     * @return the names of all the albums
     */
    fun getAlbumList(): Array<String> = data.albums.map { it.title }.toTypedArray()

    companion object {
        /**
         * Generates a set of data for testing.
         *
         * @return a instance of DisplayedAlbums which stores the data
         */
        @JvmStatic
        fun albumDataSet(): DisplayedAlbums {
            val titleList = arrayOf(
                "HQ", "The Rough Dancer and Cyclical Night",
                "The Black Light", "Symphony No.5"
            )
            val artistList = arrayOf(
                "Roy Harper", "Astor Piazzola",
                "The Black Light", "CBSO"
            )
            val isClassicalList = booleanArrayOf(false, false, false, true)
            val composerList = arrayOf<String?>(null, null, null, "Sibelius")

            val result = DisplayedAlbums()
            for (i in titleList.indices) {
                result.addAlbums(
                    titleList[i], artistList[i], isClassicalList[i], composerList[i]
                )
            }
            return result
        }
    }
}
