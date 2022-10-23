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
package com.iluwatar.temporalobject;

/**
 * Credit card is a Temporal Object implementation, with varying contract.
 *
 * @see <a href="https://martinfowler.com/eaaDev/TemporalObject.html">
 *   https://martinfowler.com/eaaDev/TemporalObject.html
 *   </a>
 */
public class CreditCard {
  private final TemporalCollection<CreditContractVersion> contractHistory;

  public CreditCard(CreditContractVersion contract, SimpleDate effectiveDate) {
    contractHistory = new TemporalCollection<>();
    contractHistory.put(effectiveDate, contract);
  }

  /**
   * Returns the contract that is being used today.
   *
   * @return The current contract.
   */
  public CreditContractVersion getContract() {
    return getContract(SimpleDate.getToday());
  }

  /**
   * Returns the contract that was used on the given date according
   * to current records.
   *
   * @param date The date to get a contract for.
   * @return The contract that was used according to current records.
   */
  public CreditContractVersion getContract(SimpleDate date) {
    return contractHistory.get(date);
  }

  /**
   * Adds the given contract to the list of contracts which is
   * effective on the given date.
   *
   * @param contract The contract to be added to this card.
   * @param effectiveDate The date that the given contract is effective from.
   */
  public void addContract(CreditContractVersion contract, SimpleDate effectiveDate) {
    contractHistory.put(effectiveDate, contract);
  }

  /**
   * Returns if the credit card has expired.
   *
   * @return True if this card has expired today.
   */
  public boolean isExpired() {
    // if expiration date before current date, return true
    return getContract().getExpirationDate().compareTo(SimpleDate.getToday()) < 0;
  }

  /**
   * Returns the current credit card number of this card.
   *
   * @return Current credit card number.
   */
  public int getNumber() {
    return getContract().getCardNumber();
  }

  /**
   * Returns the current CVC code of this card.
   *
   * @return The current CVC code.
   */
  public int getCvc() {
    return getContract().getCvcCode();
  }

  /**
   * Returns the current credit card limit of this card.
   *
   * @return The current credit limit of the card.
   */
  public int getCreditLimit() {
    return getContract().getCreditLimit();
  }

  /**
   * Returns the current expiration date of this card.
   *
   * @return The current expiration date of this card.
   */
  public SimpleDate getExpiration() {
    return getContract().getExpirationDate();
  }
}
