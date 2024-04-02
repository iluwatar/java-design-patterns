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
package com.iluwatar.service;

import com.iluwatar.api.UpdateService;
import com.iluwatar.exception.ApplicationException;
import com.iluwatar.model.Card;
import com.iluwatar.repository.JpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service to update {@link Card} entity.
 */
@RequiredArgsConstructor
public class CardUpdateService implements UpdateService<Card> {

  private final JpaRepository<Card> cardJpaRepository;

  @Override
  public Card doUpdate(Card obj, long id) {
    float additionalSum = obj.getSum();
    Card cardToUpdate = cardJpaRepository.findById(id);
    int initialVersion = cardToUpdate.getVersion();
    float resultSum = cardToUpdate.getSum() + additionalSum;
    cardToUpdate.setSum(resultSum);
    //Maybe more complex business-logic e.g. HTTP-requests and so on

    if (initialVersion != cardJpaRepository.getEntityVersionById(id)) {
      String exMessage =
          String.format("Entity with id %s were updated in another transaction", id);
      throw new ApplicationException(exMessage);
    }

    cardJpaRepository.update(cardToUpdate);
    return cardToUpdate;
  }
}
