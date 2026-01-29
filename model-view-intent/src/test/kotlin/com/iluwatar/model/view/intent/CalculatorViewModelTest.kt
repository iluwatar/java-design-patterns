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

// ABOUTME: Tests the CalculatorViewModel action handling and state transformations.
// ABOUTME: Verifies all calculator operations (add, subtract, multiply, divide, setVariable).
package com.iluwatar.model.view.intent

import com.iluwatar.model.view.intent.actions.AdditionCalculatorAction
import com.iluwatar.model.view.intent.actions.CalculatorAction
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalculatorViewModelTest {

    private fun modelAfterExecutingActions(actions: List<CalculatorAction>): CalculatorModel {
        val viewModel = CalculatorViewModel()
        for (action in actions) {
            viewModel.handleAction(action)
        }
        return viewModel.calculatorModel
    }

    @Test
    fun testSetup() {
        val model = modelAfterExecutingActions(emptyList())
        assertEquals(0.0, model.variable)
        assertEquals(0.0, model.output)
    }

    @Test
    fun testSetVariable() {
        val actions = listOf(SetVariableCalculatorAction(10.0))
        val model = modelAfterExecutingActions(actions)
        assertEquals(10.0, model.variable)
        assertEquals(0.0, model.output)
    }

    @Test
    fun testAddition() {
        val actions = listOf(
            SetVariableCalculatorAction(2.0),
            AdditionCalculatorAction,
            AdditionCalculatorAction,
            SetVariableCalculatorAction(7.0),
            AdditionCalculatorAction
        )
        val model = modelAfterExecutingActions(actions)
        assertEquals(7.0, model.variable)
        assertEquals(11.0, model.output)
    }

    @Test
    fun testSubtraction() {
        val actions = listOf(
            SetVariableCalculatorAction(2.0),
            AdditionCalculatorAction,
            AdditionCalculatorAction,
            SubtractionCalculatorAction
        )
        val model = modelAfterExecutingActions(actions)
        assertEquals(2.0, model.variable)
        assertEquals(2.0, model.output)
    }

    @Test
    fun testMultiplication() {
        val actions = listOf(
            SetVariableCalculatorAction(2.0),
            AdditionCalculatorAction,
            AdditionCalculatorAction,
            MultiplicationCalculatorAction
        )
        val model = modelAfterExecutingActions(actions)
        assertEquals(2.0, model.variable)
        assertEquals(8.0, model.output)
    }

    @Test
    fun testDivision() {
        val actions = listOf(
            SetVariableCalculatorAction(2.0),
            AdditionCalculatorAction,
            AdditionCalculatorAction,
            SetVariableCalculatorAction(2.0),
            DivisionCalculatorAction
        )
        val model = modelAfterExecutingActions(actions)
        assertEquals(2.0, model.variable)
        assertEquals(2.0, model.output)
    }
}
