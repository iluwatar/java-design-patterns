package com.iluwatar.hexagonal.domain;

/**
 * 
 * Immutable value object containing lottery player details.
 *
 */
public class PlayerDetails {

  private final String emailAddress;
  private final String bankAccountNumber;
  private final String phoneNumber;

  /**
   * Constructor.
   */
  private PlayerDetails(String email, String bankAccount, String phone) {
    emailAddress = email;
    bankAccountNumber = bankAccount;
    phoneNumber = phone;
  }
  
  /**
   * Factory for creating new objects.
   */
  public static PlayerDetails create(String email, String bankAccount, String phone) {
    return new PlayerDetails(email, bankAccount, phone);
  }
  
  /**
   * @return email
   */
  public String getEmail() {
    return emailAddress;
  }
  
  /**
   * @return bank account number
   */
  public String getBankAccount() {
    return bankAccountNumber;
  }
  
  /**
   * @return phone number
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    PlayerDetails other = (PlayerDetails) obj;
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
      if (other.phoneNumber != null) {
        return false;
      }
    } else if (!phoneNumber.equals(other.phoneNumber)) {
      return false;
    }
    return true;
  }
}
