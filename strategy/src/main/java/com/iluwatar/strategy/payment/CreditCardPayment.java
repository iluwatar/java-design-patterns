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
 * 신용카드 결제 전략 구현.
 *
 * <p>신용카드를 통한 결제를 처리합니다.
 * 실무에서는 PG사 API를 호출하여 실제 결제를 진행합니다.
 */
public class CreditCardPayment implements PaymentStrategy {

  private final String cardNumber;
  private final String cardHolderName;
  private final String cvv;
  private final String expiryDate;

  /**
   * 생성자.
   *
   * @param cardNumber 카드 번호
   * @param cardHolderName 카드 소유자명
   * @param cvv CVV 번호
   * @param expiryDate 만료일 (MM/YY)
   */
  public CreditCardPayment(String cardNumber, String cardHolderName,
                          String cvv, String expiryDate) {
    this.cardNumber = cardNumber;
    this.cardHolderName = cardHolderName;
    this.cvv = cvv;
    this.expiryDate = expiryDate;
  }

  @Override
  public PaymentResult pay(double amount) {
    System.out.println("Processing credit card payment...");
    System.out.println("Card: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
    System.out.println("Amount: $" + amount);

    // 카드 유효성 검증
    if (!validateCard()) {
      return PaymentResult.failure("Invalid card information");
    }

    // 잔액 확인 시뮬레이션
    if (!checkBalance(amount)) {
      return PaymentResult.failure("Insufficient credit limit");
    }

    // 결제 처리 시뮬레이션
    String transactionId = "CC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    System.out.println("Payment successful! Transaction ID: " + transactionId);

    return PaymentResult.success("Credit card payment completed", transactionId);
  }

  @Override
  public String getPaymentMethodName() {
    return "Credit Card";
  }

  @Override
  public boolean canProcess(double amount) {
    return amount > 0 && amount <= 10000.0; // 최대 한도 10,000
  }

  /**
   * 카드 정보 유효성 검증.
   *
   * @return 유효하면 true
   */
  private boolean validateCard() {
    // 간단한 검증 로직
    return cardNumber != null && cardNumber.length() >= 13
        && cvv != null && cvv.length() == 3
        && expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}");
  }

  /**
   * 잔액 확인 (시뮬레이션).
   *
   * @param amount 결제 금액
   * @return 잔액이 충분하면 true
   */
  private boolean checkBalance(double amount) {
    // 실제로는 카드사 API를 통해 확인
    return amount <= 5000.0; // 시뮬레이션: 5000까지 사용 가능
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }
}
