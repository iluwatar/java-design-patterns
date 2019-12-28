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

package com.iluwatar.hexagonal.domain;

/**
 * Immutable value object containing lottery player details.
 */
public class PlayerDetails {

  private final String emailAddress;
  private final String bankAccountNumber;
  private final String phoneNumber;

  /**
   * Constructor.
   */
  public PlayerDetails(String email, String bankAccount, String phone) {
    emailAddress = email;
    bankAccountNumber = bankAccount;
    phoneNumber = phone;
  }

  /**
   * Get email.
   *
   * @return email
   */
  public String getEmail() {
    return emailAddress;
  }

  /**
   * Get back account number.
   *
   * @return bank account number
   */
  public String getBankAccount() {
    return bankAccountNumber;
  }

  /**
   * Get phone number.
   *
   * @return phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public String toString() {
    return "PlayerDetails{" + "emailAddress='" + emailAddress + '\''
        + ", bankAccountNumber='" + bankAccountNumber + '\''
        + ", phoneNumber='" + phoneNumber + '\'' + '}';
  }

  @Override
  public int hashCode() {
    final var prime = 31;
    var result = 1;
    result = prime * result + ((bankAccountNumber == null) ? 0 : bankAccountNumber.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
    var other = (PlayerDetails) obj;
    if (bankAccountNumber == null) {
      if (other.bankAccountNumber != null) {
        return false;
      }
    } else if (!bankAccountNumber.equals(other.bankAccountNumber)) {
      return false;
    }
    if (emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(other.emailAddress)) {
      return false;
    }
    if (phoneNumber == null) {
      return other.phoneNumber == null;
    } else {
      return phoneNumber.equals(other.phoneNumber);
    }
  }
}
