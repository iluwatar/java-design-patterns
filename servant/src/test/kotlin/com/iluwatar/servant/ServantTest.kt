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
package com.iluwatar.servant

// ABOUTME: Tests for the Servant class verifying delegation of service actions to Royalty.
// ABOUTME: Uses MockK to verify that servant methods correctly invoke the corresponding Royalty methods.

import io.mockk.mockk
import io.mockk.verify
import io.mockk.confirmVerified
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** ServantTest */
class ServantTest {

    @Test
    fun testFeed() {
        val royalty = mockk<Royalty>(relaxed = true)
        val servant = Servant("test")
        servant.feed(royalty)
        verify { royalty.getFed() }
        confirmVerified(royalty)
    }

    @Test
    fun testGiveWine() {
        val royalty = mockk<Royalty>(relaxed = true)
        val servant = Servant("test")
        servant.giveWine(royalty)
        verify { royalty.getDrink() }
        confirmVerified(royalty)
    }

    @Test
    fun testGiveCompliments() {
        val royalty = mockk<Royalty>(relaxed = true)
        val servant = Servant("test")
        servant.giveCompliments(royalty)
        verify { royalty.receiveCompliments() }
        confirmVerified(royalty)
    }

    @Test
    fun testCheckIfYouWillBeHanged() {
        val goodMoodRoyalty = mockk<Royalty>()
        every { goodMoodRoyalty.getMood() } returns true

        val badMoodRoyalty = mockk<Royalty>()
        every { badMoodRoyalty.getMood() } returns true

        val goodCompany = listOf(goodMoodRoyalty, goodMoodRoyalty, goodMoodRoyalty)
        val badCompany = listOf(goodMoodRoyalty, goodMoodRoyalty, badMoodRoyalty)

        assertTrue(Servant("test").checkIfYouWillBeHanged(goodCompany))
        assertTrue(Servant("test").checkIfYouWillBeHanged(badCompany))
    }
}
