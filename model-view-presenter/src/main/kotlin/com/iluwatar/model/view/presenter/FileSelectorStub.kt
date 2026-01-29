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

// ABOUTME: Test stub implementation of FileSelectorView for unit testing the MVP pattern.
// ABOUTME: Provides a non-GUI implementation to verify presenter behavior without a real UI.
package com.iluwatar.model.view.presenter

/**
 * Every instance of this class represents the Stub component in the Model-View-Presenter
 * architectural pattern.
 *
 * The stub implements the View interface and it is useful when we want the test the reaction to
 * user events, such as mouse clicks.
 *
 * Since we can not test the GUI directly, the MVP pattern provides this functionality through
 * the View's dummy implementation, the Stub.
 */
class FileSelectorStub : FileSelectorView {

    /** Indicates whether or not the view is opened. */
    private var opened: Boolean = false

    /** The presenter Component. */
    private var presenter: FileSelectorPresenter? = null

    /** The current name of the file. */
    private var name: String? = ""

    /** Indicates the number of messages that were "displayed" to the user. */
    private var numOfMessageSent: Int = 0

    /** Indicates if the data of the file where displayed or not. */
    private var dataDisplayed: Boolean = false

    override fun open() {
        opened = true
    }

    override fun setPresenter(presenter: FileSelectorPresenter) {
        this.presenter = presenter
    }

    override fun isOpened(): Boolean {
        return opened
    }

    override fun getPresenter(): FileSelectorPresenter? {
        return presenter
    }

    override fun getFileName(): String? {
        return name
    }

    override fun setFileName(name: String?) {
        this.name = name
    }

    override fun showMessage(message: String) {
        numOfMessageSent++
    }

    override fun close() {
        opened = false
    }

    override fun displayData(data: String) {
        dataDisplayed = true
    }

    /** Returns the number of messages that were displayed to the user. */
    fun getMessagesSent(): Int {
        return numOfMessageSent
    }

    /**
     * Returns true, if the data were displayed.
     *
     * @return True if the data where displayed, false otherwise.
     */
    fun dataDisplayed(): Boolean {
        return dataDisplayed
    }
}
