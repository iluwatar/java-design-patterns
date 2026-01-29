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

// ABOUTME: Service implementation for updating Card entities with optimistic locking.
// ABOUTME: Checks version before update to detect concurrent modifications.
package com.iluwatar.service

import com.iluwatar.api.UpdateService
import com.iluwatar.exception.ApplicationException
import com.iluwatar.model.Card
import com.iluwatar.repository.JpaRepository

/**
 * Service to update [Card] entity.
 *
 * @property cardJpaRepository repository for card operations
 */
class CardUpdateService(
    private val cardJpaRepository: JpaRepository<Card>
) : UpdateService<Card> {

    override fun doUpdate(obj: Card, id: Long): Card {
        val additionalSum = obj.sum
        val cardToUpdate = cardJpaRepository.findById(id)
        val initialVersion = cardToUpdate.version
        val resultSum = cardToUpdate.sum + additionalSum
        cardToUpdate.sum = resultSum
        // Maybe more complex business-logic e.g. HTTP-requests and so on

        if (initialVersion != cardJpaRepository.getEntityVersionById(id)) {
            throw ApplicationException("Entity with id $id were updated in another transaction")
        }

        cardJpaRepository.update(cardToUpdate)
        return cardToUpdate
    }
}
