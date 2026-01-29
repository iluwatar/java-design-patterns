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

// ABOUTME: Concrete filter that validates the Contact Number field in an order.
// ABOUTME: Checks if the input consists of numbers and is exactly 11 digits.
package com.iluwatar.intercepting.filter

/**
 * Concrete implementation of filter This filter checks for the contact field in which it checks if
 * the input consist of numbers and it also checks if the input follows the length constraint (11
 * digits).
 */
class ContactFilter : AbstractFilter() {

    override fun execute(order: Order): String {
        val result = super.execute(order)
        val contactNumber = order.contactNumber
        return if (contactNumber == null ||
            contactNumber.matches(Regex(".*[^\\d]+.*")) ||
            contactNumber.length != 11
        ) {
            result + "Invalid contact number! "
        } else {
            result
        }
    }
}
