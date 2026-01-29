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
package com.iluwatar.delegation.simple

// ABOUTME: Entry point demonstrating the Delegation design pattern.
// ABOUTME: Shows how PrinterController delegates printing to CanonPrinter, EpsonPrinter, and HpPrinter.

import com.iluwatar.delegation.simple.printers.CanonPrinter
import com.iluwatar.delegation.simple.printers.EpsonPrinter
import com.iluwatar.delegation.simple.printers.HpPrinter

/**
 * The delegate pattern provides a mechanism to abstract away the implementation and control of the
 * desired action. The class being called in this case [PrinterController] is not responsible
 * for the actual desired action, but is actually delegated to a helper class either
 * [CanonPrinter], [EpsonPrinter] or [HpPrinter]. The consumer does not have or require
 * knowledge of the actual class carrying out the action, only the container on which they are
 * calling.
 *
 * In this example the delegates are [EpsonPrinter], [HpPrinter] and [CanonPrinter]
 * they all implement [Printer]. The [PrinterController] class also implements [Printer].
 * However, neither provide the functionality of [Printer] by printing to the screen, they
 * actually call upon the instance of [Printer] that they were instantiated with. Therefore,
 * delegating the behaviour to another class.
 */
private const val MESSAGE_TO_PRINT = "hello world"

fun main() {
    val hpPrinterController = PrinterController(HpPrinter())
    val canonPrinterController = PrinterController(CanonPrinter())
    val epsonPrinterController = PrinterController(EpsonPrinter())

    hpPrinterController.print(MESSAGE_TO_PRINT)
    canonPrinterController.print(MESSAGE_TO_PRINT)
    epsonPrinterController.print(MESSAGE_TO_PRINT)
}
