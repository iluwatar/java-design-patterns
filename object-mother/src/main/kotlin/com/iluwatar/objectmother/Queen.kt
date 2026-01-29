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

// ABOUTME: Defines all attributes and behaviour related to the Queen.
// ABOUTME: Implements Royalty interface with flirtiness state for responding to King's flirts.

/** Defines all attributes and behaviour related to the Queen. */
class Queen : Royalty {
    private var isDrunk = false
    private var isHappy = false
    var isFlirty = false

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

    /**
     * Method which is called when the king is flirting to a queen.
     *
     * @param king King who initialized the flirt.
     * @return A value which describes if the flirt was successful or not.
     */
    fun getFlirted(king: King): Boolean = this.isFlirty && king.isHappy && !king.isDrunk
}
