/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.hexagonal.notifications;

import com.iluwatar.hexagonal.domain.PlayerDetails;

/**
 * 
 * Provides notifications for lottery events.
 *
 */
public interface LotteryNotifications {

  /**
   * Notify lottery ticket was submitted
   */
  void notifyTicketSubmitted(PlayerDetails details);

  /**
   * Notify there was an error submitting lottery ticket
   */
  void notifyTicketSubmitError(PlayerDetails details);

  /**
   * Notify lottery ticket did not win
   */
  void notifyNoWin(PlayerDetails details);

  /**
   * Notify that prize has been paid
   */
  void notifyPrize(PlayerDetails details, int prizeAmount);

  /**
   * Notify that there was an error paying the prize
   */
  void notifyPrizeError(PlayerDetails details, int prizeAmount);

}
