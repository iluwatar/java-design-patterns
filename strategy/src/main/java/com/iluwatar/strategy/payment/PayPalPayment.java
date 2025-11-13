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
 * PayPal 결제 전략 구현.
 *
 * <p>PayPal 계정을 통한 결제를 처리합니다.
 * 실무에서는 PayPal API를 사용하여 OAuth 인증 후 결제를 진행합니다.
 */
public class PayPalPayment implements PaymentStrategy {

  private final String email;
  private final String password;

  /**
   * 생성자.
   *
   * @param email PayPal 이메일
   * @param password PayPal 비밀번호
   */
  public PayPalPayment(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public PaymentResult pay(double amount) {
    System.out.println("Processing PayPal payment...");
    System.out.println("Email: " + maskEmail(email));
    System.out.println("Amount: $" + amount);

    // 로그인 검증
    if (!authenticate()) {
      return PaymentResult.failure("PayPal authentication failed");
    }

    // 잔액 확인 시뮬레이션
    if (!checkPayPalBalance(amount)) {
      return PaymentResult.failure("Insufficient PayPal balance");
    }

    // 결제 처리 시뮬레이션
    String transactionId = "PP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    System.out.println("PayPal payment successful! Transaction ID: " + transactionId);

    return PaymentResult.success("PayPal payment completed", transactionId);
  }

  @Override
  public String getPaymentMethodName() {
    return "PayPal";
  }

  @Override
  public boolean canProcess(double amount) {
    return amount > 0 && amount <= 25000.0; // 최대 한도 25,000
  }

  /**
   * PayPal 인증 (시뮬레이션).
   *
   * @return 인증 성공하면 true
   */
  private boolean authenticate() {
    // 실제로는 PayPal OAuth API를 사용
    return email != null && email.contains("@")
        && password != null && password.length() >= 6;
  }

  /**
   * PayPal 잔액 확인 (시뮬레이션).
   *
   * @param amount 결제 금액
   * @return 잔액이 충분하면 true
   */
  private boolean checkPayPalBalance(double amount) {
    // 실제로는 PayPal API를 통해 확인
    return amount <= 10000.0; // 시뮬레이션: 10,000까지 사용 가능
  }

  /**
   * 이메일 마스킹.
   *
   * @param email 이메일
   * @return 마스킹된 이메일
   */
  private String maskEmail(String email) {
    if (email == null || !email.contains("@")) {
      return "***@***.com";
    }
    String[] parts = email.split("@");
    String username = parts[0];
    String masked = username.substring(0, Math.min(3, username.length())) + "***";
    return masked + "@" + parts[1];
  }

  public String getEmail() {
    return email;
  }
}
