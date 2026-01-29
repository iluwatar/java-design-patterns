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

// ABOUTME: Swing JFrame client that handles user input and runs it through filters.
// ABOUTME: Pre-processes the request through FilterManager before displaying in Target.
package com.iluwatar.intercepting.filter

import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

/**
 * The Client class is responsible for handling the input and running them through filters inside
 * the [FilterManager].
 *
 * This is where [Filter]s come to play as the client pre-processes the request before
 * being displayed in the [Target].
 */
class Client : JFrame("Client System") { // NOSONAR

    @Transient
    private var filterManager: FilterManager? = null
    private val jl: JLabel
    private val jtFields: Array<JTextField>
    private val jtAreas: Array<JTextArea>
    private val clearButton: JButton
    private val processButton: JButton

    /** Constructor. */
    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setSize(300, 300)
        jl = JLabel("RUNNING...")
        jtFields = Array(3) { JTextField() }
        jtAreas = Array(2) { JTextArea() }
        clearButton = JButton("Clear")
        processButton = JButton("Process")

        setup()
    }

    private fun setup() {
        layout = BorderLayout()
        val panel = JPanel()
        add(jl, BorderLayout.SOUTH)
        add(panel, BorderLayout.CENTER)
        panel.layout = GridLayout(6, 2)
        panel.add(JLabel("Name"))
        panel.add(jtFields[0])
        panel.add(JLabel("Contact Number"))
        panel.add(jtFields[1])
        panel.add(JLabel("Address"))
        panel.add(jtAreas[0])
        panel.add(JLabel("Deposit Number"))
        panel.add(jtFields[2])
        panel.add(JLabel("Order"))
        panel.add(jtAreas[1])
        panel.add(clearButton)
        panel.add(processButton)

        clearButton.addActionListener {
            jtAreas.forEach { it.text = "" }
            jtFields.forEach { it.text = "" }
        }

        processButton.addActionListener { e -> actionPerformed(e) }

        val rootPane = SwingUtilities.getRootPane(processButton)
        rootPane.defaultButton = processButton
        isVisible = true
    }

    fun setFilterManager(filterManager: FilterManager) {
        this.filterManager = filterManager
    }

    fun sendRequest(order: Order): String {
        return filterManager!!.filterRequest(order)
    }

    private fun actionPerformed(@Suppress("UNUSED_PARAMETER") e: ActionEvent) {
        val fieldText1 = jtFields[0].text
        val fieldText2 = jtFields[1].text
        val areaText1 = jtAreas[0].text
        val fieldText3 = jtFields[2].text
        val areaText2 = jtAreas[1].text
        val order = Order(fieldText1, fieldText2, areaText1, fieldText3, areaText2)
        jl.text = sendRequest(order)
    }
}
