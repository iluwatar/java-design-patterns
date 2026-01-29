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
package com.iluwatar.collectingparameter

// ABOUTME: Tests the collecting parameter pattern logic with various print job scenarios.
// ABOUTME: Verifies that only policy-conforming items are collected across A4, A3, and A2 filters.

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.util.LinkedList

class CollectingParameterTest {
    @BeforeEach
    fun setUp() {
        PrinterQueue.emptyQueue()
    }

    @Test
    @Timeout(1000)
    fun testCollectingParameter() {
        val item1 = PrinterItem(PaperSizes.A4, 1, isDoubleSided = false, isColour = true)
        val item2 = PrinterItem(PaperSizes.A4, 10, isDoubleSided = true, isColour = false)
        val item3 = PrinterItem(PaperSizes.A4, 4, isDoubleSided = true, isColour = true)
        val item4 = PrinterItem(PaperSizes.A3, 9, isDoubleSided = false, isColour = false)
        val item5 = PrinterItem(PaperSizes.A3, 3, isDoubleSided = true, isColour = true)
        val item6 = PrinterItem(PaperSizes.A3, 3, isDoubleSided = false, isColour = true)
        val item7 = PrinterItem(PaperSizes.A3, 3, isDoubleSided = true, isColour = false)
        val item8 = PrinterItem(PaperSizes.A2, 1, isDoubleSided = false, isColour = false)
        val item9 = PrinterItem(PaperSizes.A2, 2, isDoubleSided = false, isColour = false)
        val item10 = PrinterItem(PaperSizes.A2, 1, isDoubleSided = true, isColour = false)
        val item11 = PrinterItem(PaperSizes.A2, 1, isDoubleSided = false, isColour = true)

        PrinterQueue.addPrinterItem(item1)
        PrinterQueue.addPrinterItem(item2)
        PrinterQueue.addPrinterItem(item3)
        PrinterQueue.addPrinterItem(item4)
        PrinterQueue.addPrinterItem(item5)
        PrinterQueue.addPrinterItem(item6)
        PrinterQueue.addPrinterItem(item7)
        PrinterQueue.addPrinterItem(item8)
        PrinterQueue.addPrinterItem(item9)
        PrinterQueue.addPrinterItem(item10)
        PrinterQueue.addPrinterItem(item11)

        val result = LinkedList<PrinterItem>()
        addValidA4Papers(result)
        addValidA3Papers(result)
        addValidA2Papers(result)

        val testResult = LinkedList<PrinterItem>()
        testResult.add(item1)
        testResult.add(item2)
        testResult.add(item4)
        testResult.add(item8)

        assertArrayEquals(testResult.toTypedArray(), result.toTypedArray())
    }
}