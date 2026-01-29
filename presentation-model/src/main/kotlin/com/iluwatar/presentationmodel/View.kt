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

// ABOUTME: GUI view class that displays album information using Swing components.
// ABOUTME: Connects to PresentationModel for data binding and user interaction handling.
package com.iluwatar.presentationmodel

import io.github.oshai.kotlinlogging.KotlinLogging
import java.awt.TextField
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.Box
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JFrame
import javax.swing.JList
import javax.swing.WindowConstants

private val logger = KotlinLogging.logger {}

/**
 * Generates the GUI of albums.
 */
class View {
    /** the model that controls this view. */
    val model: PresentationModel = PresentationModel(PresentationModel.albumDataSet())

    /** the field to show and modify title. */
    lateinit var txtTitle: TextField
        private set

    /** the field to show and modify the name of artist. */
    lateinit var txtArtist: TextField
        private set

    /** the checkbox for is classical. */
    lateinit var chkClassical: JCheckBox
        private set

    /** the field to show and modify composer. */
    lateinit var txtComposer: TextField
        private set

    /** a list to show all the name of album. */
    private lateinit var albumList: JList<String>

    /** a button to apply of all the change. */
    private lateinit var apply: JButton

    /** roll back the change. */
    private lateinit var cancel: JButton

    companion object {
        /** the value of the text field size. */
        const val WIDTH_TXT: Int = 200
        const val HEIGHT_TXT: Int = 50

        /** the value of the GUI size and location. */
        const val LOCATION_X: Int = 200
        const val LOCATION_Y: Int = 200
        const val WIDTH: Int = 500
        const val HEIGHT: Int = 300
    }

    /** Save the data to PresentationModel. */
    fun saveToMod() {
        logger.info { "Save data to PresentationModel" }
        model.setArtist(txtArtist.text)
        model.setTitle(txtTitle.text)
        model.setIsClassical(chkClassical.isSelected)
        model.setComposer(txtComposer.text)
    }

    /** Load the data from PresentationModel. */
    fun loadFromMod() {
        logger.info { "Load data from PresentationModel" }
        txtArtist.text = model.getArtist()
        txtTitle.text = model.getTitle()
        chkClassical.isSelected = model.getIsClassical()
        txtComposer.isEditable = model.getIsClassical()
        txtComposer.text = model.getComposer()
    }

    /** Initialize the GUI. */
    fun createView() {
        val frame = JFrame("Album")
        val b1 = Box.createHorizontalBox()

        frame.add(b1)
        albumList = JList(model.getAlbumList())
        albumList.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                model.setSelectedAlbumNumber(albumList.selectedIndex + 1)
                loadFromMod()
            }
        })
        b1.add(albumList)

        val b2 = Box.createVerticalBox()
        b1.add(b2)

        txtArtist = TextField()
        txtTitle = TextField()

        txtArtist.setSize(WIDTH_TXT, HEIGHT_TXT)
        txtTitle.setSize(WIDTH_TXT, HEIGHT_TXT)

        chkClassical = JCheckBox()
        txtComposer = TextField()
        chkClassical.addActionListener {
            txtComposer.isEditable = chkClassical.isSelected
            if (!chkClassical.isSelected) {
                txtComposer.text = ""
            }
        }
        txtComposer.setSize(WIDTH_TXT, HEIGHT_TXT)
        txtComposer.isEditable = model.getIsClassical()

        apply = JButton("Apply")
        apply.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                saveToMod()
                loadFromMod()
            }
        })
        cancel = JButton("Cancel")
        cancel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                loadFromMod()
            }
        })

        b2.add(txtArtist)
        b2.add(txtTitle)

        b2.add(chkClassical)
        b2.add(txtComposer)

        b2.add(apply)
        b2.add(cancel)

        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.setBounds(LOCATION_X, LOCATION_Y, WIDTH, HEIGHT)
        frame.isVisible = true
    }
}
