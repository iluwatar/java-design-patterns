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

// ABOUTME: Unit tests for the PresentationModel class.
// ABOUTME: Tests album selection, property getters/setters, and album list generation.
package com.iluwatar.presentationmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Arrays

class PresentationTest {
    private val albumList = arrayOf(
        "HQ", "The Rough Dancer and Cyclical Night", "The Black Light", "Symphony No.5"
    )

    @Test
    fun testCreateAlbumList() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val list = model.getAlbumList()
        assertEquals(Arrays.toString(albumList), Arrays.toString(list))
    }

    @Test
    fun testSetSelectedAlbumNumber_1() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val selectId = 2
        model.setSelectedAlbumNumber(selectId)
        assertEquals(albumList[selectId - 1], model.getTitle())
    }

    @Test
    fun testSetSelectedAlbumNumber_2() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val selectId = 4
        model.setSelectedAlbumNumber(selectId)
        assertEquals(albumList[selectId - 1], model.getTitle())
    }

    @Test
    fun testSetTitle_1() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val testTitle = "TestTile"
        model.setTitle(testTitle)
        assertEquals(testTitle, model.getTitle())
    }

    @Test
    fun testSetTitle_2() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val testTitle = ""
        model.setTitle(testTitle)
        assertEquals(testTitle, model.getTitle())
    }

    @Test
    fun testSetArtist_1() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val testArtist = "TestArtist"
        model.setArtist(testArtist)
        assertEquals(testArtist, model.getArtist())
    }

    @Test
    fun testSetArtist_2() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val testArtist = ""
        model.setArtist(testArtist)
        assertEquals(testArtist, model.getArtist())
    }

    @Test
    fun testSetIsClassical() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        model.setIsClassical(true)
        assertTrue(model.getIsClassical())
    }

    @Test
    fun testSetComposer_false() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val testComposer = "TestComposer"

        model.setIsClassical(false)
        model.setComposer(testComposer)
        assertEquals("", model.getComposer())
    }

    @Test
    fun testSetComposer_true() {
        val model = PresentationModel(PresentationModel.albumDataSet())
        val testComposer = "TestComposer"

        model.setIsClassical(true)
        model.setComposer(testComposer)
        assertEquals(testComposer, model.getComposer())
    }
}
