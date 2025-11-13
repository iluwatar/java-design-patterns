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

import java.time.LocalDateTime;

/**
 * 결제 결과를 나타내는 클래스.
 */
public class PaymentResult {

  private final boolean success;
  private final String message;
  private final String transactionId;
  private final LocalDateTime timestamp;

  /**
   * 생성자.
   *
   * @param success 결제 성공 여부
   * @param message 결과 메시지
   * @param transactionId 거래 ID
   */
  public PaymentResult(boolean success, String message, String transactionId) {
    this.success = success;
    this.message = message;
    this.transactionId = transactionId;
    this.timestamp = LocalDateTime.now();
  }

  /**
   * 성공한 결제 결과 생성.
   *
   * @param message 성공 메시지
   * @param transactionId 거래 ID
   * @return 성공 결과
   */
  public static PaymentResult success(String message, String transactionId) {
    return new PaymentResult(true, message, transactionId);
  }

  /**
   * 실패한 결제 결과 생성.
   *
   * @param message 실패 메시지
   * @return 실패 결과
   */
  public static PaymentResult failure(String message) {
    return new PaymentResult(false, message, null);
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "PaymentResult{"
        + "success=" + success
        + ", message='" + message + '\''
        + ", transactionId='" + transactionId + '\''
        + ", timestamp=" + timestamp
        + '}';
  }
}
