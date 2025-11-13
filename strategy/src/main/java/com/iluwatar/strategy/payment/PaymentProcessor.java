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

/**
 * 실무 예제: 결제 처리 시스템 (Strategy Pattern Context).
 *
 * <p>PaymentProcessor는 다양한 결제 전략을 사용하여 결제를 처리합니다.
 * 런타임에 결제 방법을 변경할 수 있어 유연한 결제 시스템을 구현할 수 있습니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>전자상거래 결제 시스템</li>
 *   <li>구독 서비스 결제</li>
 *   <li>모바일 앱 인앱 결제</li>
 *   <li>다국가 결제 처리</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * // 신용카드 결제
 * PaymentStrategy creditCard = new CreditCardPayment(
 *     "1234-5678-9012-3456", "John Doe", "123", "12/25"
 * );
 * PaymentProcessor processor = new PaymentProcessor(creditCard);
 * PaymentResult result = processor.processPayment(100.0);
 *
 * // 런타임에 결제 방법 변경
 * processor.setPaymentStrategy(new PayPalPayment("user@example.com", "password"));
 * result = processor.processPayment(200.0);
 * </pre>
 *
 * <h2>장점:</h2>
 * <ul>
 *   <li>런타임에 결제 방법 변경 가능</li>
 *   <li>새로운 결제 수단 추가 용이 (OCP 준수)</li>
 *   <li>결제 로직과 비즈니스 로직 분리</li>
 *   <li>각 결제 수단별 독립적인 테스트 가능</li>
 * </ul>
 */
public class PaymentProcessor {

  private PaymentStrategy paymentStrategy;

  /**
   * 생성자.
   *
   * @param paymentStrategy 결제 전략
   */
  public PaymentProcessor(PaymentStrategy paymentStrategy) {
    this.paymentStrategy = paymentStrategy;
  }

  /**
   * 결제 전략 설정.
   * 런타임에 결제 방법을 변경할 수 있습니다.
   *
   * @param paymentStrategy 새로운 결제 전략
   */
  public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
    this.paymentStrategy = paymentStrategy;
  }

  /**
   * 현재 결제 전략 조회.
   *
   * @return 현재 결제 전략
   */
  public PaymentStrategy getPaymentStrategy() {
    return paymentStrategy;
  }

  /**
   * 결제 처리.
   *
   * @param amount 결제 금액
   * @return 결제 결과
   */
  public PaymentResult processPayment(double amount) {
    if (paymentStrategy == null) {
      return PaymentResult.failure("No payment method selected");
    }

    if (amount <= 0) {
      return PaymentResult.failure("Invalid payment amount");
    }

    if (!paymentStrategy.canProcess(amount)) {
      return PaymentResult.failure(
          "Amount exceeds limit for " + paymentStrategy.getPaymentMethodName()
      );
    }

    System.out.println("========================================");
    System.out.println("Starting payment with: " + paymentStrategy.getPaymentMethodName());
    System.out.println("========================================");

    PaymentResult result = paymentStrategy.pay(amount);

    System.out.println("========================================");
    System.out.println("Payment " + (result.isSuccess() ? "SUCCESS" : "FAILED"));
    System.out.println("Message: " + result.getMessage());
    if (result.getTransactionId() != null) {
      System.out.println("Transaction ID: " + result.getTransactionId());
    }
    System.out.println("========================================\n");

    return result;
  }

  /**
   * 결제 가능 여부 확인.
   *
   * @param amount 결제 금액
   * @return 결제 가능하면 true
   */
  public boolean canProcessPayment(double amount) {
    return paymentStrategy != null && paymentStrategy.canProcess(amount);
  }

  /**
   * 현재 선택된 결제 수단 이름 조회.
   *
   * @return 결제 수단 이름
   */
  public String getCurrentPaymentMethod() {
    return paymentStrategy != null ? paymentStrategy.getPaymentMethodName() : "None";
  }
}
