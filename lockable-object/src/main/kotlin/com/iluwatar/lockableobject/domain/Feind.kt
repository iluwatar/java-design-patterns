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

// ABOUTME: Runnable task representing a creature trying to acquire a lockable object.
// ABOUTME: Implements fight logic where creatures duel until one acquires the target.
package com.iluwatar.lockableobject.domain

import com.iluwatar.lockableobject.Lockable
import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/**
 * A Feind is a creature that wants to possess a Lockable object.
 */
class Feind(
    private val creature: Creature,
    private val target: Lockable
) : Runnable {

    private val random = SecureRandom()

    override fun run() {
        if (!creature.acquire(target)) {
            target.getLocker()?.let { holder ->
                fightForTheSword(creature, holder, target)
            }
        } else {
            logger.info { "${target.getLocker()?.name} has acquired the sword!" }
        }
    }

    /**
     * Keeps on fighting until the Lockable is possessed.
     *
     * @param reacher as the source creature.
     * @param holder as the foe.
     * @param sword as the Lockable to possess.
     */
    private fun fightForTheSword(reacher: Creature, holder: Creature, sword: Lockable) {
        logger.info { "A duel between ${reacher.name} and ${holder.name} has been started!" }
        while (target.isLocked() && reacher.isAlive() && holder.isAlive()) {
            val randBool = random.nextBoolean()
            if (randBool) {
                reacher.attack(holder)
            } else {
                holder.attack(reacher)
            }
        }
        if (reacher.isAlive()) {
            if (!reacher.acquire(sword)) {
                sword.getLocker()?.let { newHolder ->
                    fightForTheSword(reacher, newHolder, sword)
                }
            } else {
                logger.info { "${reacher.name} has acquired the sword!" }
            }
        }
    }
}
