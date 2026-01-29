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

// ABOUTME: Swing JFrame that displays validated order requests in a table.
// ABOUTME: This is where the requests are displayed after being validated by filters.
package com.iluwatar.intercepting.filter

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import javax.swing.table.DefaultTableModel

/** This is where the requests are displayed after being validated by filters. */
class Target : JFrame("Order System") { // NOSONAR

    private val jt: JTable
    private val dtm: DefaultTableModel
    private val del: JButton

    /** Constructor. */
    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setSize(640, 480)
        dtm = DefaultTableModel(
            arrayOf("Name", "Contact Number", "Address", "Deposit Number", "Order"),
            0
        )
        jt = JTable(dtm)
        del = JButton("Delete")
        setup()
    }

    private fun setup() {
        layout = BorderLayout()
        val bot = JPanel()
        add(jt.tableHeader, BorderLayout.NORTH)
        bot.layout = BorderLayout()
        bot.add(del, BorderLayout.EAST)
        add(bot, BorderLayout.SOUTH)
        val jsp = JScrollPane(jt)
        jsp.preferredSize = Dimension(500, 250)
        add(jsp, BorderLayout.CENTER)

        del.addActionListener(TargetListener())

        val rootPane = SwingUtilities.getRootPane(del)
        rootPane.defaultButton = del
        isVisible = true
    }

    fun execute(request: Array<String>) {
        dtm.addRow(arrayOf(request[0], request[1], request[2], request[3], request[4]))
    }

    internal inner class TargetListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val temp = jt.selectedRow
            if (temp == -1) {
                return
            }
            val temp2 = jt.selectedRowCount
            repeat(temp2) { dtm.removeRow(temp) }
        }
    }
}
