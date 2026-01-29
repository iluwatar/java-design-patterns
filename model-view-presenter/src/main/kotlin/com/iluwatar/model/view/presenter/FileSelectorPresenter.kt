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

// ABOUTME: Presenter component in MVP pattern that handles user interactions.
// ABOUTME: Coordinates between View and Model, responding to user actions and updating the View.
package com.iluwatar.model.view.presenter

import java.io.Serializable

/**
 * Every instance of this class represents the Presenter component in the Model-View-Presenter
 * architectural pattern.
 *
 * It is responsible for reacting to the user's actions and update the View component.
 */
class FileSelectorPresenter(
    /** The View component that the presenter interacts with. */
    private val view: FileSelectorView
) : Serializable {

    /** The Model component that the presenter interacts with. */
    var loader: FileLoader? = null

    /** Starts the presenter. */
    fun start() {
        view.setPresenter(this)
        view.open()
    }

    /** An "event" that fires when the name of the file to be loaded changes. */
    fun fileNameChanged() {
        loader?.fileName = view.getFileName()
    }

    /** Ok button handler. */
    fun confirmed() {
        val currentLoader = loader ?: return
        if (currentLoader.fileName.isNullOrEmpty()) {
            view.showMessage("Please give the name of the file first!")
            return
        }

        if (currentLoader.fileExists()) {
            val data = currentLoader.loadData()
            if (data != null) {
                view.displayData(data)
            }
        } else {
            view.showMessage("The file specified does not exist.")
        }
    }

    /** Cancels the file loading process. */
    fun cancelled() {
        view.close()
    }

    companion object {
        private const val serialVersionUID = 1210314339075855074L
    }
}
