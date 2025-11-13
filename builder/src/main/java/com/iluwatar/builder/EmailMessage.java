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

package com.iluwatar.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 실무 예제: 이메일 메시지 빌더
 *
 * <p>EmailMessage는 이메일을 구성하는 Builder 패턴 예제입니다.
 * 복잡한 이메일 메시지를 단계적으로 구성할 수 있습니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>이메일 발송 시스템</li>
 *   <li>알림 서비스</li>
 *   <li>마케팅 이메일 생성</li>
 *   <li>시스템 알림 메일</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * EmailMessage email = EmailMessage.builder()
 *     .from("sender@example.com")
 *     .to("recipient@example.com")
 *     .subject("Welcome to our service!")
 *     .body("Thank you for joining us.")
 *     .addCc("manager@example.com")
 *     .addBcc("admin@example.com")
 *     .priority(Priority.HIGH)
 *     .html(true)
 *     .build();
 *
 * // 이메일 전송 시뮬레이션
 * boolean sent = email.send();
 * </pre>
 *
 * <h2>장점:</h2>
 * <ul>
 *   <li>복잡한 이메일 구성을 명확하게 표현</li>
 *   <li>선택적 수신자(CC, BCC) 쉽게 추가</li>
 *   <li>첨부파일 관리 용이</li>
 *   <li>불변 객체로 안전한 이메일 전송</li>
 * </ul>
 */
public final class EmailMessage {

  private final String from;
  private final List<String> to;
  private final List<String> cc;
  private final List<String> bcc;
  private final String subject;
  private final String body;
  private final boolean isHtml;
  private final Priority priority;
  private final List<String> attachments;
  private final LocalDateTime createdAt;

  /**
   * 이메일 우선순위 열거형.
   */
  public enum Priority {
    LOW, NORMAL, HIGH, URGENT
  }

  /**
   * Private 생성자 - Builder를 통해서만 객체 생성 가능.
   */
  private EmailMessage(Builder builder) {
    this.from = builder.from;
    this.to = Collections.unmodifiableList(new ArrayList<>(builder.to));
    this.cc = Collections.unmodifiableList(new ArrayList<>(builder.cc));
    this.bcc = Collections.unmodifiableList(new ArrayList<>(builder.bcc));
    this.subject = builder.subject;
    this.body = builder.body;
    this.isHtml = builder.isHtml;
    this.priority = builder.priority;
    this.attachments = Collections.unmodifiableList(new ArrayList<>(builder.attachments));
    this.createdAt = LocalDateTime.now();
  }

  /**
   * Builder 인스턴스 생성.
   *
   * @return EmailMessage Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  // Getters
  public String getFrom() {
    return from;
  }

  public List<String> getTo() {
    return to;
  }

  public List<String> getCc() {
    return cc;
  }

  public List<String> getBcc() {
    return bcc;
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  public boolean isHtml() {
    return isHtml;
  }

  public Priority getPriority() {
    return priority;
  }

  public List<String> getAttachments() {
    return attachments;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * 총 수신자 수 계산.
   *
   * @return 총 수신자 수 (TO + CC + BCC)
   */
  public int getTotalRecipientCount() {
    return to.size() + cc.size() + bcc.size();
  }

  /**
   * 첨부파일 존재 여부.
   *
   * @return 첨부파일이 있으면 true
   */
  public boolean hasAttachments() {
    return !attachments.isEmpty();
  }

  /**
   * 이메일 전송 시뮬레이션.
   *
   * @return 전송 성공 여부
   */
  public boolean send() {
    System.out.println("=".repeat(50));
    System.out.println("Sending Email:");
    System.out.println("From: " + from);
    System.out.println("To: " + String.join(", ", to));

    if (!cc.isEmpty()) {
      System.out.println("CC: " + String.join(", ", cc));
    }
    if (!bcc.isEmpty()) {
      System.out.println("BCC: " + String.join(", ", bcc));
    }

    System.out.println("Subject: " + subject);
    System.out.println("Priority: " + priority);
    System.out.println("Format: " + (isHtml ? "HTML" : "Plain Text"));

    if (!attachments.isEmpty()) {
      System.out.println("Attachments: " + String.join(", ", attachments));
    }

    System.out.println("\nBody:");
    System.out.println(body);
    System.out.println("=".repeat(50));
    System.out.println("Email sent successfully!");

    return true;
  }

  @Override
  public String toString() {
    return "EmailMessage{"
        + "from='" + from + '\''
        + ", to=" + to.size() + " recipients"
        + ", subject='" + subject + '\''
        + ", priority=" + priority
        + ", attachments=" + attachments.size()
        + ", createdAt=" + createdAt
        + '}';
  }

  /**
   * EmailMessage Builder 클래스.
   */
  public static class Builder {
    private String from;
    private List<String> to = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private String subject;
    private String body;
    private boolean isHtml = false;
    private Priority priority = Priority.NORMAL;
    private List<String> attachments = new ArrayList<>();

    /**
     * 발신자 설정 (필수).
     *
     * @param from 발신자 이메일
     * @return Builder
     */
    public Builder from(String from) {
      this.from = from;
      return this;
    }

    /**
     * 수신자 추가 (필수).
     *
     * @param to 수신자 이메일
     * @return Builder
     */
    public Builder to(String to) {
      this.to.add(to);
      return this;
    }

    /**
     * 여러 수신자 추가.
     *
     * @param recipients 수신자 목록
     * @return Builder
     */
    public Builder toMultiple(List<String> recipients) {
      this.to.addAll(recipients);
      return this;
    }

    /**
     * 참조(CC) 추가.
     *
     * @param cc 참조 이메일
     * @return Builder
     */
    public Builder addCc(String cc) {
      this.cc.add(cc);
      return this;
    }

    /**
     * 숨은참조(BCC) 추가.
     *
     * @param bcc 숨은참조 이메일
     * @return Builder
     */
    public Builder addBcc(String bcc) {
      this.bcc.add(bcc);
      return this;
    }

    /**
     * 제목 설정 (필수).
     *
     * @param subject 이메일 제목
     * @return Builder
     */
    public Builder subject(String subject) {
      this.subject = subject;
      return this;
    }

    /**
     * 본문 설정 (필수).
     *
     * @param body 이메일 본문
     * @return Builder
     */
    public Builder body(String body) {
      this.body = body;
      return this;
    }

    /**
     * HTML 형식 설정.
     *
     * @param isHtml HTML 형식 여부
     * @return Builder
     */
    public Builder html(boolean isHtml) {
      this.isHtml = isHtml;
      return this;
    }

    /**
     * 우선순위 설정.
     *
     * @param priority 이메일 우선순위
     * @return Builder
     */
    public Builder priority(Priority priority) {
      this.priority = priority;
      return this;
    }

    /**
     * 첨부파일 추가.
     *
     * @param filePath 첨부파일 경로
     * @return Builder
     */
    public Builder addAttachment(String filePath) {
      this.attachments.add(filePath);
      return this;
    }

    /**
     * 여러 첨부파일 추가.
     *
     * @param filePaths 첨부파일 경로 목록
     * @return Builder
     */
    public Builder attachments(List<String> filePaths) {
      this.attachments.addAll(filePaths);
      return this;
    }

    /**
     * EmailMessage 객체 생성.
     * 필수 필드 유효성 검증 수행.
     *
     * @return EmailMessage 객체
     * @throws IllegalStateException 필수 필드가 없거나 유효하지 않은 경우
     */
    public EmailMessage build() {
      if (from == null || from.trim().isEmpty()) {
        throw new IllegalStateException("From address is required");
      }
      if (!isValidEmail(from)) {
        throw new IllegalStateException("Invalid from email address");
      }
      if (to.isEmpty()) {
        throw new IllegalStateException("At least one recipient is required");
      }
      for (String recipient : to) {
        if (!isValidEmail(recipient)) {
          throw new IllegalStateException("Invalid recipient email address: " + recipient);
        }
      }
      if (subject == null || subject.trim().isEmpty()) {
        throw new IllegalStateException("Subject is required");
      }
      if (body == null || body.trim().isEmpty()) {
        throw new IllegalStateException("Body is required");
      }
      return new EmailMessage(this);
    }

    /**
     * 이메일 주소 유효성 검증 (간단한 검증).
     *
     * @param email 이메일 주소
     * @return 유효하면 true
     */
    private boolean isValidEmail(String email) {
      return email != null && email.contains("@") && email.contains(".");
    }
  }
}
