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

public class LotteryNotificationsImpl implements LotteryNotifications {

  @Override
  public void notifyTicketSubmitted(PlayerDetails details) {
    System.out.println(String.format("Lottery ticket for %s was submitted. Bank account %s was charged for 3 credits.",
        details.getEmail(), details.getBankAccount()));
  }

  @Override
  public void notifyNoWin(PlayerDetails details) {
    System.out.println(String.format("Lottery ticket for %s was checked and unfortunately did not win this time.",
        details.getEmail()));
  }

  @Override
  public void notifyPrize(PlayerDetails details, int prizeAmount) {
    System.out
        .println(String.format("Lottery ticket for %s has won! The bank account %s was deposited with %d credits.",
            details.getEmail(), details.getBankAccount(), prizeAmount));
  }

  @Override
  public void notifyPrizeError(PlayerDetails details, int prizeAmount) {
    System.out
        .println(String.format("Lottery ticket for %s has won! Unfortunately the bank credit transfer of %d failed.",
            details.getEmail(), prizeAmount));
  }

  @Override
  public void notifyTicketSubmitError(PlayerDetails details) {
    System.out.println(
        String.format("Lottery ticket for %s could not be submitted because the credit transfer of 3 credits failed.",
            details.getEmail()));
  }
}
