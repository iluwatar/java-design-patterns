/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.servant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Date: 12/28/15 - 10:02 PM
 *
 * @author Jeroen Meulemeester
 */
public class ServantTest {

  @Test
  public void testFeed() {
    final var royalty = mock(Royalty.class);
    final var servant = new Servant("test");
    servant.feed(royalty);
    verify(royalty).getFed();
    verifyNoMoreInteractions(royalty);
  }

  @Test
  public void testGiveWine() {
    final var royalty = mock(Royalty.class);
    final var servant = new Servant("test");
    servant.giveWine(royalty);
    verify(royalty).getDrink();
    verifyNoMoreInteractions(royalty);
  }

  @Test
  public void testGiveCompliments() {
    final var royalty = mock(Royalty.class);
    final var servant = new Servant("test");
    servant.giveCompliments(royalty);
    verify(royalty).receiveCompliments();
    verifyNoMoreInteractions(royalty);
  }

  @Test
  public void testCheckIfYouWillBeHanged() {
    final var goodMoodRoyalty = mock(Royalty.class);
    when(goodMoodRoyalty.getMood()).thenReturn(true);

    final var badMoodRoyalty = mock(Royalty.class);
    when(badMoodRoyalty.getMood()).thenReturn(true);

    final var goodCompany = List.of(goodMoodRoyalty, goodMoodRoyalty, goodMoodRoyalty);

    final var badCompany = List.of(goodMoodRoyalty, goodMoodRoyalty, badMoodRoyalty);

    assertTrue(new Servant("test").checkIfYouWillBeHanged(goodCompany));
    assertTrue(new Servant("test").checkIfYouWillBeHanged(badCompany));

  }

}