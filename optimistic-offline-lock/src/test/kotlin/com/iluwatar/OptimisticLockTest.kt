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

// ABOUTME: Unit tests for the optimistic offline lock pattern implementation.
// ABOUTME: Tests both successful updates and version conflict detection scenarios.
package com.iluwatar

import com.iluwatar.exception.ApplicationException
import com.iluwatar.model.Card
import com.iluwatar.repository.JpaRepository
import com.iluwatar.service.CardUpdateService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OptimisticLockTest {

    private lateinit var cardUpdateService: CardUpdateService
    private lateinit var cardRepository: JpaRepository<Card>

    @BeforeEach
    fun setUp() {
        cardRepository = mockk(relaxed = true)
        cardUpdateService = CardUpdateService(cardRepository)
    }

    @Test
    fun shouldNotUpdateEntityOnDifferentVersion() {
        val initialVersion = 1
        val cardId = 123L
        val card = Card(id = cardId, version = initialVersion, sum = 123f)

        every { cardRepository.findById(cardId) } returns card
        every { cardRepository.getEntityVersionById(cardId) } returns initialVersion + 1

        assertThrows(ApplicationException::class.java) {
            cardUpdateService.doUpdate(card, cardId)
        }
    }

    @Test
    fun shouldUpdateOnSameVersion() {
        val initialVersion = 1
        val cardId = 123L
        val card = Card(id = cardId, version = initialVersion, sum = 123f)

        every { cardRepository.findById(cardId) } returns card
        every { cardRepository.getEntityVersionById(cardId) } returns initialVersion

        cardUpdateService.doUpdate(card, cardId)

        verify { cardRepository.update(any()) }
    }
}
