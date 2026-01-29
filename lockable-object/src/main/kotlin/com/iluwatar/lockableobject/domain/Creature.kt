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

// ABOUTME: Abstract base class for all creatures that can fight and acquire lockable objects.
// ABOUTME: Provides thread-safe attack, hit, kill, and acquire operations with synchronized methods.
package com.iluwatar.lockableobject.domain

import com.iluwatar.lockableobject.Lockable
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * An abstract class of a creature that wanders across the wasteland. It can attack, get hit and
 * acquire a Lockable object.
 */
abstract class Creature(var name: String) {

    var type: CreatureType? = null
    var health: Int = 0
    var damage: Int = 0
    internal var instruments: MutableSet<Lockable> = HashSet()

    /**
     * Reaches for the Lockable and tried to hold it.
     *
     * @param lockable as the Lockable to lock.
     * @return true of Lockable was locked by this creature.
     */
    fun acquire(lockable: Lockable): Boolean {
        if (lockable.lock(this)) {
            instruments.add(lockable)
            return true
        }
        return false
    }

    /**
     * Terminates the Creature and unlocks all the Lockable that it possesses.
     */
    @Synchronized
    fun kill() {
        logger.info { "$type $name has been slayed!" }
        for (lockable in instruments) {
            lockable.unlock(this)
        }
        instruments.clear()
    }

    /**
     * Attacks a foe.
     *
     * @param creature as the foe to be attacked.
     */
    @Synchronized
    fun attack(creature: Creature) {
        creature.hit(damage)
    }

    /**
     * When a creature gets hit it removed the amount of damage from the creature's life.
     *
     * @param damage as the damage that was taken.
     */
    @Synchronized
    fun hit(damage: Int) {
        require(damage >= 0) { "Damage cannot be a negative number" }
        if (isAlive()) {
            health -= damage
            if (!isAlive()) {
                kill()
            }
        }
    }

    /**
     * Checks if the creature is still alive.
     *
     * @return true of creature is alive.
     */
    @Synchronized
    fun isAlive(): Boolean = health > 0
}
