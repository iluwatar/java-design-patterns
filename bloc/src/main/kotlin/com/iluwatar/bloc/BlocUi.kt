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

// ABOUTME: Handles the creation and management of the UI components for the BLoC pattern demo.
// ABOUTME: Creates a Swing-based counter UI that demonstrates state management with listeners.
package com.iluwatar.bloc

import java.awt.BorderLayout
import java.awt.Font
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.WindowConstants

/**
 * The BlocUi class handles the creation and management of the UI components.
 */
class BlocUi {
    /**
     * Creates and shows the UI.
     */
    fun createAndShowUi() {
        val bloc = Bloc()

        val frame =
            JFrame("BloC example").apply {
                defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
                setSize(400, 300)
            }

        val counterLabel =
            JLabel("Counter: 0", SwingConstants.CENTER).apply {
                font = Font("Arial", Font.BOLD, 20)
            }

        val decrementButton = JButton("Decrement")
        val toggleListenerButton = JButton("Disable Listener")
        val incrementButton = JButton("Increment")

        frame.layout = BorderLayout()
        frame.add(counterLabel, BorderLayout.CENTER)
        frame.add(incrementButton, BorderLayout.NORTH)
        frame.add(decrementButton, BorderLayout.SOUTH)
        frame.add(toggleListenerButton, BorderLayout.EAST)

        val stateListener =
            StateListener<State> { state ->
                counterLabel.text = "Counter: ${state.value}"
            }

        bloc.addListener(stateListener)

        toggleListenerButton.addActionListener {
            if (stateListener in bloc.getListeners()) {
                bloc.removeListener(stateListener)
                toggleListenerButton.text = "Enable Listener"
            } else {
                bloc.addListener(stateListener)
                toggleListenerButton.text = "Disable Listener"
            }
        }

        incrementButton.addActionListener { bloc.increment() }
        decrementButton.addActionListener { bloc.decrement() }

        frame.isVisible = true
    }
}