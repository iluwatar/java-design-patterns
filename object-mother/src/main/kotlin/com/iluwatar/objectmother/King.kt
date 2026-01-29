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
package com.iluwatar.objectmother

// ABOUTME: Defines all attributes and behaviour related to the King.
// ABOUTME: Implements Royalty interface with flirting capability towards a Queen.

/** Defines all attributes and behaviour related to the King. */
class King : Royalty {
    internal var isDrunk = false
    internal var isHappy = false

    override fun makeDrunk() {
        isDrunk = true
    }

    override fun makeSober() {
        isDrunk = false
    }

    override fun makeHappy() {
        isHappy = true
    }

    override fun makeUnhappy() {
        isHappy = false
    }

    fun isHappy(): Boolean = isHappy

    /**
     * Method to flirt to a queen.
     *
     * @param queen Queen which should be flirted.
     */
    fun flirt(queen: Queen) {
        val flirtStatus = queen.getFlirted(this)
        if (!flirtStatus) {
            this.makeUnhappy()
        } else {
            this.makeHappy()
        }
    }
}
