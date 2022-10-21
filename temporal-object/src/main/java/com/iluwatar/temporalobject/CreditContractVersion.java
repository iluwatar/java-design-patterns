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
 * CreditContractVersion is a wrapper for several temporal properties of a contract.
 */
public class CreditContractVersion {
  private final String contractText;
  private final String contractIssuer;
  private final String version;
  private final int creditLimit;
  private final int cardNumber;
  private final int cvcCode;
  private final SimpleDate expirationDate;

  CreditContractVersion(String contractText, String contractIssuer,
                        String version, int creditLimit, int cardNumber, int cvcCode,
                        SimpleDate expirationDate) {
    this.contractText = contractText;
    this.contractIssuer = contractIssuer;
    this.version = version;
    this.creditLimit = creditLimit;
    this.cardNumber = cardNumber;
    this.cvcCode = cvcCode;
    this.expirationDate = expirationDate;
  }

  public String getContractIssuer() {
    return contractIssuer;
  }

  public String getContractText() {
    return contractText;
  }

  public String getVersion() {
    return version;
  }

  public int getCreditLimit() {
    return creditLimit;
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public int getCvcCode() {
    return cvcCode;
  }

  public SimpleDate getExpirationDate() {
    return expirationDate;
  }
}
