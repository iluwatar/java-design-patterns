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

// ABOUTME: Implementation of a Lockable object representing Aragorn's legendary sword.
// ABOUTME: Uses synchronized blocks to ensure thread-safe locking/unlocking operations.
package com.iluwatar.lockableobject

import com.iluwatar.lockableobject.domain.Creature
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * An implementation of a Lockable object. This is the Sword of Aragorn and every creature wants to
 * possess it!
 */
class SwordOfAragorn : Lockable {

    private var locker: Creature? = null
    private val synchronizer = Any()

    companion object {
        private const val NAME = "The Sword of Aragorn"
    }

    override fun isLocked(): Boolean = locker != null

    override fun lock(creature: Creature): Boolean {
        synchronized(synchronizer) {
            logger.info { "${creature.name} is now trying to acquire ${getName()}!" }
            if (!isLocked()) {
                locker = creature
                return true
            } else {
                if (locker?.name != creature.name) {
                    return false
                }
            }
        }
        return false
    }

    override fun unlock(creature: Creature) {
        synchronized(synchronizer) {
            if (locker != null && locker?.name == creature.name) {
                locker = null
                logger.info { "${getName()} is now free!" }
            }
            if (locker != null) {
                throw LockingException("You cannot unlock an object you are not the owner of.")
            }
        }
    }

    override fun getLocker(): Creature? = locker

    override fun getName(): String = NAME
}
