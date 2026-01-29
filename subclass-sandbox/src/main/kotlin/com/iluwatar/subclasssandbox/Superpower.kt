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
package com.iluwatar.subclasssandbox

// ABOUTME: Abstract base class providing sandbox operations for superpower subclasses.
// ABOUTME: Subclasses implement activate() using the protected move, playSound, and spawnParticles methods.

import io.github.oshai.kotlinlogging.KLogger

/**
 * Superpower abstract class. In this class the basic operations of all types of superpowers are
 * provided as protected methods.
 */
abstract class Superpower {

    protected abstract val logger: KLogger

    /**
     * Subclass of superpower should implement this sandbox method by calling the methods provided in
     * this super class.
     */
    internal abstract fun activate()

    /** Move to ([x], [y], [z]). */
    internal fun move(x: Double, y: Double, z: Double) {
        logger.info { "Move to ( $x, $y, $z )" }
    }

    /** Play sound effect for the superpower. */
    internal fun playSound(soundName: String, volume: Int) {
        logger.info { "Play $soundName with volume $volume" }
    }

    /** Spawn particles for the superpower. */
    internal fun spawnParticles(particleType: String, count: Int) {
        logger.info { "Spawn $count particle with type $particleType" }
    }
}
