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

// ABOUTME: Handles transformations to CalculatorModel based on intercepted CalculatorAction.
// ABOUTME: Implements the intent processing logic of the MVI pattern using sealed when.
package com.iluwatar.model.view.intent

import com.iluwatar.model.view.intent.actions.AdditionCalculatorAction
import com.iluwatar.model.view.intent.actions.CalculatorAction
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction

/**
 * Handle transformations to [CalculatorModel] based on intercepted [CalculatorAction].
 */
class CalculatorViewModel {

    /** Current calculator model (can be changed). */
    var calculatorModel: CalculatorModel = CalculatorModel(0.0, 0.0)
        private set

    /**
     * Handle calculator action.
     * @param action transforms calculator model.
     */
    internal fun handleAction(action: CalculatorAction) {
        when (action) {
            is AdditionCalculatorAction -> add()
            is SubtractionCalculatorAction -> subtract()
            is MultiplicationCalculatorAction -> multiply()
            is DivisionCalculatorAction -> divide()
            is SetVariableCalculatorAction -> setVariable(action.variable)
        }
    }

    /**
     * Set calculator model variable.
     * @param variable value of the calculator model variable.
     */
    private fun setVariable(variable: Double) {
        calculatorModel = calculatorModel.copy(variable = variable)
    }

    /** Add variable to model output. */
    private fun add() {
        calculatorModel = calculatorModel.copy(
            output = calculatorModel.output + calculatorModel.variable
        )
    }

    /** Subtract variable from model output. */
    private fun subtract() {
        calculatorModel = calculatorModel.copy(
            output = calculatorModel.output - calculatorModel.variable
        )
    }

    /** Multiply model output by variable. */
    private fun multiply() {
        calculatorModel = calculatorModel.copy(
            output = calculatorModel.output * calculatorModel.variable
        )
    }

    /** Divide model output by variable. */
    private fun divide() {
        calculatorModel = calculatorModel.copy(
            output = calculatorModel.output / calculatorModel.variable
        )
    }
}
