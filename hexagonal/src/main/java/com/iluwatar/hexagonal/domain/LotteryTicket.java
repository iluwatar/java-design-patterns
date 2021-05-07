/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.hexagonal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Immutable value object representing lottery ticket.
 */
@Getter
@ToString
@RequiredArgsConstructor
public class LotteryTicket {

  private final LotteryTicketId id;
  private final PlayerDetails playerDetails;
  private final LotteryNumbers lotteryNumbers;

  @Override
  public int hashCode() {
    final var prime = 31;
    var result = 1;
    result = prime * result + ((lotteryNumbers == null) ? 0 : lotteryNumbers.hashCode());
    result = prime * result + ((playerDetails == null) ? 0 : playerDetails.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    var other = (LotteryTicket) obj;
    if (lotteryNumbers == null) {
      if (other.lotteryNumbers != null) {
        return false;
      }
    } else if (!lotteryNumbers.equals(other.lotteryNumbers)) {
      return false;
    }
    if (playerDetails == null) {
      return other.playerDetails == null;
    } else {
      return playerDetails.equals(other.playerDetails);
    }
  }

}
