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
package com.iluwatar;

import com.iluwatar.exception.ApplicationException;
import com.iluwatar.model.Card;
import com.iluwatar.repository.JpaRepository;
import com.iluwatar.service.CardUpdateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

@SuppressWarnings({"rawtypes", "unchecked"})
public class OptimisticLockTest {

  private CardUpdateService cardUpdateService;

  private JpaRepository cardRepository;

  @BeforeEach
  public void setUp() {
    cardRepository = Mockito.mock(JpaRepository.class);
    cardUpdateService = new CardUpdateService(cardRepository);
  }

  @Test
  public void shouldNotUpdateEntityOnDifferentVersion() {
    int initialVersion = 1;
    long cardId = 123L;
    Card card = Card.builder()
        .id(cardId)
        .version(initialVersion)
        .sum(123f)
        .build();
    when(cardRepository.findById(eq(cardId))).thenReturn(card);
    when(cardRepository.getEntityVersionById(Mockito.eq(cardId))).thenReturn(initialVersion + 1);

    Assertions.assertThrows(ApplicationException.class,
        () -> cardUpdateService.doUpdate(card, cardId));
  }

  @Test
  public void shouldUpdateOnSameVersion() {
    int initialVersion = 1;
    long cardId = 123L;
    Card card = Card.builder()
        .id(cardId)
        .version(initialVersion)
        .sum(123f)
        .build();
    when(cardRepository.findById(eq(cardId))).thenReturn(card);
    when(cardRepository.getEntityVersionById(Mockito.eq(cardId))).thenReturn(initialVersion);

    cardUpdateService.doUpdate(card, cardId);

    Mockito.verify(cardRepository).update(Mockito.any());
  }
}
