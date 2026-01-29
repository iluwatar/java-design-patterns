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
package com.iluwatar.memento

// ABOUTME: Originator class that uses mementos to store and restore its internal state.
// ABOUTME: Models a star progressing through lifecycle stages with save/restore capability.

/** Star uses "mementos" to store and restore state. */
class Star(
    private var type: StarType,
    private var ageYears: Int,
    private var massTons: Int
) {

    /** Makes time pass for the star. */
    fun timePasses() {
        ageYears *= 2
        massTons *= 8
        when (type) {
            StarType.RED_GIANT -> type = StarType.WHITE_DWARF
            StarType.SUN -> type = StarType.RED_GIANT
            StarType.SUPERNOVA -> type = StarType.DEAD
            StarType.WHITE_DWARF -> type = StarType.SUPERNOVA
            StarType.DEAD -> {
                ageYears *= 2
                massTons = 0
            }
        }
    }

    fun getMemento(): StarMemento = StarMementoInternal(
        type = type,
        ageYears = ageYears,
        massTons = massTons
    )

    fun setMemento(memento: StarMemento) {
        val state = memento as StarMementoInternal
        type = state.type
        ageYears = state.ageYears
        massTons = state.massTons
    }

    override fun toString(): String = "$type age: $ageYears years mass: $massTons tons"

    /** StarMemento implementation. */
    private data class StarMementoInternal(
        val type: StarType,
        val ageYears: Int,
        val massTons: Int
    ) : StarMemento
}
