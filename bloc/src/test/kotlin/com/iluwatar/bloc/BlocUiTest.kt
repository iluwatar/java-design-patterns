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

// ABOUTME: UI tests for the BlocUi class verifying button interactions and state updates.
// ABOUTME: Tests increment, decrement, and toggle listener button functionality using Swing.
package com.iluwatar.bloc

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.WindowConstants

class BlocUiTest {
    private lateinit var frame: JFrame
    private lateinit var counterLabel: JLabel
    private lateinit var incrementButton: JButton
    private lateinit var decrementButton: JButton
    private lateinit var toggleListenerButton: JButton
    private lateinit var bloc: Bloc
    private lateinit var stateListener: StateListener<State>

    @BeforeEach
    fun setUp() {
        bloc = Bloc()

        frame =
            JFrame("BloC example").apply {
                defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
                setSize(400, 300)
            }

        counterLabel =
            JLabel("Counter: 0", SwingConstants.CENTER).apply {
                font = Font("Arial", Font.BOLD, 20)
            }

        incrementButton = JButton("Increment")
        decrementButton = JButton("Decrement")
        toggleListenerButton = JButton("Disable Listener")

        frame.layout = BorderLayout()
        frame.add(counterLabel, BorderLayout.CENTER)
        frame.add(incrementButton, BorderLayout.NORTH)
        frame.add(decrementButton, BorderLayout.SOUTH)
        frame.add(toggleListenerButton, BorderLayout.EAST)

        stateListener = StateListener { state -> counterLabel.text = "Counter: ${state.value}" }
        bloc.addListener(stateListener)

        incrementButton.addActionListener { bloc.increment() }
        decrementButton.addActionListener { bloc.decrement() }
        toggleListenerButton.addActionListener {
            if (stateListener in bloc.getListeners()) {
                bloc.removeListener(stateListener)
                toggleListenerButton.text = "Enable Listener"
            } else {
                bloc.addListener(stateListener)
                toggleListenerButton.text = "Disable Listener"
            }
        }

        frame.isVisible = true
    }

    @AfterEach
    fun tearDown() {
        frame.dispose()
        bloc = Bloc()
    }

    @Test
    fun testIncrementButton() {
        simulateButtonClick(incrementButton)
        assertEquals("Counter: 1", counterLabel.text)
    }

    @Test
    fun testDecrementButton() {
        simulateButtonClick(decrementButton)
        assertEquals("Counter: -1", counterLabel.text)
    }

    @Test
    fun testToggleListenerButton() {
        // Disable listener
        simulateButtonClick(toggleListenerButton)
        simulateButtonClick(incrementButton)
        assertEquals("Counter: 0", counterLabel.text) // Listener is disabled

        // Enable listener
        simulateButtonClick(toggleListenerButton)
        simulateButtonClick(incrementButton)
        assertEquals("Counter: 2", counterLabel.text) // Listener is re-enabled
    }

    private fun simulateButtonClick(button: JButton) {
        button.actionListeners.forEach { it.actionPerformed(null) }
    }
}