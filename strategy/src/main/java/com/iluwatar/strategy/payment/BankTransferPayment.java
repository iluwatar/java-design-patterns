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

package com.iluwatar.strategy.payment;

import java.util.UUID;

/**
 * 계좌이체 결제 전략 구현.
 *
 * <p>은행 계좌를 통한 직접 이체 결제를 처리합니다.
 * 실무에서는 오픈뱅킹 API를 사용하여 실시간 계좌이체를 진행합니다.
 */
public class BankTransferPayment implements PaymentStrategy {

  private final String bankName;
  private final String accountNumber;
  private final String accountHolderName;

  /**
   * 생성자.
   *
   * @param bankName 은행명
   * @param accountNumber 계좌번호
   * @param accountHolderName 예금주명
   */
  public BankTransferPayment(String bankName, String accountNumber, String accountHolderName) {
    this.bankName = bankName;
    this.accountNumber = accountNumber;
    this.accountHolderName = accountHolderName;
  }

  @Override
  public PaymentResult pay(double amount) {
    System.out.println("Processing bank transfer payment...");
    System.out.println("Bank: " + bankName);
    System.out.println("Account: " + maskAccountNumber(accountNumber));
    System.out.println("Holder: " + accountHolderName);
    System.out.println("Amount: $" + amount);

    // 계좌 유효성 검증
    if (!validateAccount()) {
      return PaymentResult.failure("Invalid account information");
    }

    // 잔액 확인 시뮬레이션
    if (!checkAccountBalance(amount)) {
      return PaymentResult.failure("Insufficient account balance");
    }

    // 이체 처리 시뮬레이션
    String transactionId = "BT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    System.out.println("Transfer successful! Transaction ID: " + transactionId);

    return PaymentResult.success("Bank transfer completed", transactionId);
  }

  @Override
  public String getPaymentMethodName() {
    return "Bank Transfer (" + bankName + ")";
  }

  @Override
  public boolean canProcess(double amount) {
    return amount > 0 && amount <= 100000.0; // 최대 한도 100,000
  }

  /**
   * 계좌 정보 유효성 검증.
   *
   * @return 유효하면 true
   */
  private boolean validateAccount() {
    return bankName != null && !bankName.isEmpty()
        && accountNumber != null && accountNumber.length() >= 10
        && accountHolderName != null && !accountHolderName.isEmpty();
  }

  /**
   * 계좌 잔액 확인 (시뮬레이션).
   *
   * @param amount 이체 금액
   * @return 잔액이 충분하면 true
   */
  private boolean checkAccountBalance(double amount) {
    // 실제로는 은행 API를 통해 확인
    return amount <= 50000.0; // 시뮬레이션: 50,000까지 이체 가능
  }

  /**
   * 계좌번호 마스킹.
   *
   * @param accountNumber 계좌번호
   * @return 마스킹된 계좌번호
   */
  private String maskAccountNumber(String accountNumber) {
    if (accountNumber == null || accountNumber.length() < 4) {
      return "****";
    }
    return "****-****-" + accountNumber.substring(accountNumber.length() - 4);
  }

  public String getBankName() {
    return bankName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }
}
