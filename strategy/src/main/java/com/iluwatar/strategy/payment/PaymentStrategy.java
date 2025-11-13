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
 * 결제 전략 인터페이스.
 *
 * <p>Strategy 패턴의 핵심 인터페이스로, 다양한 결제 방법을 추상화합니다.
 * 각 결제 수단은 이 인터페이스를 구현하여 동일한 방식으로 처리될 수 있습니다.
 */
public interface PaymentStrategy {

  /**
   * 결제 처리.
   *
   * @param amount 결제 금액
   * @return 결제 성공 여부
   */
  PaymentResult pay(double amount);

  /**
   * 결제 수단 이름 조회.
   *
   * @return 결제 수단 이름
   */
  String getPaymentMethodName();

  /**
   * 결제 가능 여부 확인.
   *
   * @param amount 결제 금액
   * @return 결제 가능하면 true
   */
  boolean canProcess(double amount);
}
