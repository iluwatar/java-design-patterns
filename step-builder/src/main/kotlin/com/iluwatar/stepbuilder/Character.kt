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

// ABOUTME: Data class representing a game character with various attributes.
// ABOUTME: Used to demonstrate the Step Builder pattern for guided object construction.
package com.iluwatar.stepbuilder

/**
 * The class with many parameters.
 */
class Character(var name: String) {
    var fighterClass: String? = null
    var wizardClass: String? = null
    var weapon: String? = null
    var spell: String? = null
    var abilities: List<String>? = null

    override fun toString(): String {
        return buildString {
            append("This is a ")
            append(fighterClass ?: wizardClass)
            append(" named ")
            append(name)
            append(" armed with a ")
            append(weapon ?: spell ?: "with nothing")
            if (abilities != null) {
                append(" and wielding $abilities abilities")
            }
            append('.')
        }
    }
}
