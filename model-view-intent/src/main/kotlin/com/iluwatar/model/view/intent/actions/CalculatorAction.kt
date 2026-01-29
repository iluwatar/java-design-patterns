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

// ABOUTME: Defines the sealed interface for calculator actions in the MVI pattern.
// ABOUTME: Uses Kotlin sealed interface to represent all possible user intents.
package com.iluwatar.model.view.intent.actions

/**
 * Defines what outside interactions can be consumed by view model.
 * Uses Kotlin sealed interface for exhaustive when expressions.
 */
sealed interface CalculatorAction

/** Addition [CalculatorAction]. */
data object AdditionCalculatorAction : CalculatorAction

/** Subtraction [CalculatorAction]. */
data object SubtractionCalculatorAction : CalculatorAction

/** Multiplication [CalculatorAction]. */
data object MultiplicationCalculatorAction : CalculatorAction

/** Division [CalculatorAction]. */
data object DivisionCalculatorAction : CalculatorAction

/**
 * SetVariable [CalculatorAction].
 * @property variable the variable value to set in the calculator model.
 */
data class SetVariableCalculatorAction(val variable: Double) : CalculatorAction
