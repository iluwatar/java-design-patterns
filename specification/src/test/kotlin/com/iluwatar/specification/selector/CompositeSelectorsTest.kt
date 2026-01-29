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

// ABOUTME: Test class for composite selector functionality (AND, OR, NOT).
// ABOUTME: Verifies conjunction, disjunction, and negation selector compositions.
package com.iluwatar.specification.selector

import com.iluwatar.specification.creature.Creature
import com.iluwatar.specification.property.Mass
import com.iluwatar.specification.property.Movement
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CompositeSelectorsTest {

    /** Verify if the conjunction selector gives the correct results. */
    @Test
    fun testAndComposition() {
        val swimmingHeavyCreature = mockk<Creature>()
        every { swimmingHeavyCreature.movement } returns Movement.SWIMMING
        every { swimmingHeavyCreature.mass } returns Mass(100.0)

        val swimmingLightCreature = mockk<Creature>()
        every { swimmingLightCreature.movement } returns Movement.SWIMMING
        every { swimmingLightCreature.mass } returns Mass(25.0)

        val lightAndSwimmingSelector =
            MassSmallerThanOrEqSelector(50.0).and(MovementSelector(Movement.SWIMMING))
        assertFalse(lightAndSwimmingSelector.test(swimmingHeavyCreature))
        assertTrue(lightAndSwimmingSelector.test(swimmingLightCreature))
    }

    /** Verify if the disjunction selector gives the correct results. */
    @Test
    fun testOrComposition() {
        val swimmingHeavyCreature = mockk<Creature>()
        every { swimmingHeavyCreature.movement } returns Movement.SWIMMING
        every { swimmingHeavyCreature.mass } returns Mass(100.0)

        val swimmingLightCreature = mockk<Creature>()
        every { swimmingLightCreature.movement } returns Movement.SWIMMING
        every { swimmingLightCreature.mass } returns Mass(25.0)

        val lightOrSwimmingSelector =
            MassSmallerThanOrEqSelector(50.0).or(MovementSelector(Movement.SWIMMING))
        assertTrue(lightOrSwimmingSelector.test(swimmingHeavyCreature))
        assertTrue(lightOrSwimmingSelector.test(swimmingLightCreature))
    }

    /** Verify if the negation selector gives the correct results. */
    @Test
    fun testNotComposition() {
        val swimmingHeavyCreature = mockk<Creature>()
        every { swimmingHeavyCreature.movement } returns Movement.SWIMMING
        every { swimmingHeavyCreature.mass } returns Mass(100.0)

        val swimmingLightCreature = mockk<Creature>()
        every { swimmingLightCreature.movement } returns Movement.SWIMMING
        every { swimmingLightCreature.mass } returns Mass(25.0)

        val heavySelector = MassSmallerThanOrEqSelector(50.0).not()
        assertTrue(heavySelector.test(swimmingHeavyCreature))
        assertFalse(heavySelector.test(swimmingLightCreature))
    }
}
