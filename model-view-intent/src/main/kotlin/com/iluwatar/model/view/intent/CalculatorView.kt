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

// ABOUTME: Exposes user interactions to CalculatorViewModel through CalculatorAction.
// ABOUTME: Displays the calculator model output and delegates actions to the view model.
package com.iluwatar.model.view.intent

import com.iluwatar.model.view.intent.actions.AdditionCalculatorAction
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Exposes changes to the state of calculator to [CalculatorViewModel] through
 * [com.iluwatar.model.view.intent.actions.CalculatorAction] and displays its updated [CalculatorModel].
 *
 * @property viewModel view model param handling the operations.
 */
class CalculatorView(val viewModel: CalculatorViewModel) {

    /** Display current view model output with logger. */
    internal fun displayTotal() {
        logger.info { "Total value = ${viewModel.calculatorModel.output}" }
    }

    /** Handle addition action. */
    internal fun add() {
        viewModel.handleAction(AdditionCalculatorAction)
    }

    /** Handle subtraction action. */
    internal fun subtract() {
        viewModel.handleAction(SubtractionCalculatorAction)
    }

    /** Handle multiplication action. */
    internal fun multiply() {
        viewModel.handleAction(MultiplicationCalculatorAction)
    }

    /** Handle division action. */
    internal fun divide() {
        viewModel.handleAction(DivisionCalculatorAction)
    }

    /**
     * Handle setting variable action.
     * @param value the calculator variable to set.
     */
    internal fun setVariable(value: Double) {
        viewModel.handleAction(SetVariableCalculatorAction(value))
    }
}
