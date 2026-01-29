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

// ABOUTME: Unit tests for the View class.
// ABOUTME: Tests save/load functionality and data binding between view and presentation model.
package com.iluwatar.presentationmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ViewTest {
    private val albumList = arrayOf(
        "HQ", "The Rough Dancer and Cyclical Night", "The Black Light", "Symphony No.5"
    )

    @Test
    fun testSave_setArtistAndTitle() {
        val view = View()
        view.createView()
        val testTitle = "testTitle"
        val testArtist = "testArtist"
        view.txtArtist.text = testArtist
        view.txtTitle.text = testTitle
        view.saveToMod()
        view.loadFromMod()
        assertEquals(testTitle, view.model.getTitle())
        assertEquals(testArtist, view.model.getArtist())
    }

    @Test
    fun testSave_setClassicalAndComposer() {
        val view = View()
        view.createView()
        val isClassical = true
        val testComposer = "testComposer"
        view.chkClassical.isSelected = isClassical
        view.txtComposer.text = testComposer
        view.saveToMod()
        view.loadFromMod()
        assertTrue(view.model.getIsClassical())
        assertEquals(testComposer, view.model.getComposer())
    }

    @Test
    fun testLoad_1() {
        val view = View()
        view.createView()
        view.model.setSelectedAlbumNumber(2)
        view.loadFromMod()
        assertEquals(albumList[1], view.model.getTitle())
    }

    @Test
    fun testLoad_2() {
        val view = View()
        view.createView()
        view.model.setSelectedAlbumNumber(4)
        view.loadFromMod()
        assertEquals(albumList[3], view.model.getTitle())
    }
}
