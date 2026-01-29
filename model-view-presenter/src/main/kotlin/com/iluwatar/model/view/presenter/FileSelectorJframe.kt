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

// ABOUTME: Swing JFrame implementation of the FileSelectorView interface.
// ABOUTME: Provides the GUI for file selection with text input, display area, and action buttons.
package com.iluwatar.model.view.presenter

import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
import javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
import javax.swing.WindowConstants

/**
 * This class is the GUI implementation of the View component in the Model-View-Presenter pattern.
 */
class FileSelectorJframe : JFrame("File Loader"), FileSelectorView, ActionListener {

    /** The "OK" button for loading the file. */
    private val ok: JButton

    /** The cancel button. */
    private val cancel: JButton

    /** The text field for giving the name of the file that we want to open. */
    private val input: JTextField

    /** A text area that will keep the contents of the file opened. */
    private val area: JTextArea

    /** The Presenter component that the frame will interact with. */
    private var presenter: FileSelectorPresenter? = null

    /** The name of the file that we want to read it's contents. */
    private var fileName: String? = null

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        layout = null
        setBounds(100, 100, 500, 200)

        // Add the panel.
        val panel = JPanel()
        panel.layout = null
        add(panel)
        panel.setBounds(0, 0, 500, 200)
        panel.background = Color.LIGHT_GRAY

        // Add the info label.
        val info = JLabel("File Name :")
        panel.add(info)
        info.setBounds(30, 10, 100, 30)

        // Add the contents label.
        val contents = JLabel("File contents :")
        panel.add(contents)
        contents.setBounds(30, 100, 120, 30)

        // Add the text field.
        input = JTextField(100)
        panel.add(input)
        input.setBounds(150, 15, 200, 20)

        // Add the text area.
        area = JTextArea(100, 100)
        val pane = JScrollPane(area)
        pane.horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED
        pane.verticalScrollBarPolicy = VERTICAL_SCROLLBAR_AS_NEEDED
        panel.add(pane)
        area.isEditable = false
        pane.setBounds(150, 100, 250, 80)

        // Add the OK button.
        ok = JButton("OK")
        panel.add(ok)
        ok.setBounds(250, 50, 100, 25)
        ok.addActionListener(this)

        // Add the cancel button.
        cancel = JButton("Cancel")
        panel.add(cancel)
        cancel.setBounds(380, 50, 100, 25)
        cancel.addActionListener(this)
    }

    override fun actionPerformed(e: ActionEvent) {
        when (e.source) {
            ok -> {
                fileName = input.text
                presenter?.fileNameChanged()
                presenter?.confirmed()
            }
            cancel -> {
                presenter?.cancelled()
            }
        }
    }

    override fun open() {
        isVisible = true
    }

    override fun close() {
        dispose()
    }

    override fun isOpened(): Boolean {
        return isVisible
    }

    override fun setPresenter(presenter: FileSelectorPresenter) {
        this.presenter = presenter
    }

    override fun getPresenter(): FileSelectorPresenter? {
        return presenter
    }

    override fun setFileName(name: String?) {
        fileName = name
    }

    override fun getFileName(): String? {
        return fileName
    }

    override fun showMessage(message: String) {
        JOptionPane.showMessageDialog(null, message)
    }

    override fun displayData(data: String) {
        area.text = data
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
