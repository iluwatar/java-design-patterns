/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

/**
 * 
 * Immutable value object representing lottery ticket.
 *
 */
public class LotteryTicket {

  private LotteryTicketId id;
  private final PlayerDetails playerDetails;
  private final LotteryNumbers lotteryNumbers;

  /**
   * Constructor.
   */
  public LotteryTicket(LotteryTicketId id, PlayerDetails details, LotteryNumbers numbers) {
    this.id = id;
    playerDetails = details;
    lotteryNumbers = numbers;
  }

  /**
   * @return player details
   */
  public PlayerDetails getPlayerDetails() {
    return playerDetails;
  }
  
  /**
   * @return lottery numbers
   */
  public LotteryNumbers getNumbers() {
    return lotteryNumbers;
  }

  /**
   * @return id
   */
  public LotteryTicketId getId() {
    return id;
  }

  /**
   * set id
   */
  public void setId(LotteryTicketId id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return playerDetails.toString() + " " + lotteryNumbers.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    LotteryTicket other = (LotteryTicket) obj;
    if (lotteryNumbers == null) {
      if (other.lotteryNumbers != null) {
        return false;
      }
    } else if (!lotteryNumbers.equals(other.lotteryNumbers)) {
      return false;
    }
    if (playerDetails == null) {
      if (other.playerDetails != null) {
        return false;
      }
    } else if (!playerDetails.equals(other.playerDetails)) {
      return false;
    }
    return true;
  }
}
