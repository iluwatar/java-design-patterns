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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.builder.EmailMessage.Priority;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * EmailMessage 테스트.
 */
class EmailMessageTest {

  @Test
  void testBasicEmailCreation() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test Subject")
        .body("Test Body")
        .build();

    // Then
    assertNotNull(email);
    assertEquals("sender@example.com", email.getFrom());
    assertEquals(1, email.getTo().size());
    assertEquals("recipient@example.com", email.getTo().get(0));
    assertEquals("Test Subject", email.getSubject());
    assertEquals("Test Body", email.getBody());
    assertEquals(Priority.NORMAL, email.getPriority());
    assertFalse(email.isHtml());
  }

  @Test
  void testEmailWithMultipleRecipients() {
    // Given
    List<String> recipients = Arrays.asList(
        "user1@example.com",
        "user2@example.com",
        "user3@example.com"
    );

    // When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .toMultiple(recipients)
        .subject("Test Subject")
        .body("Test Body")
        .build();

    // Then
    assertEquals(3, email.getTo().size());
    assertTrue(email.getTo().contains("user1@example.com"));
    assertTrue(email.getTo().contains("user2@example.com"));
    assertTrue(email.getTo().contains("user3@example.com"));
  }

  @Test
  void testEmailWithCc() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .addCc("cc1@example.com")
        .addCc("cc2@example.com")
        .subject("Test Subject")
        .body("Test Body")
        .build();

    // Then
    assertEquals(2, email.getCc().size());
    assertTrue(email.getCc().contains("cc1@example.com"));
    assertTrue(email.getCc().contains("cc2@example.com"));
  }

  @Test
  void testEmailWithBcc() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .addBcc("bcc1@example.com")
        .addBcc("bcc2@example.com")
        .subject("Test Subject")
        .body("Test Body")
        .build();

    // Then
    assertEquals(2, email.getBcc().size());
    assertTrue(email.getBcc().contains("bcc1@example.com"));
    assertTrue(email.getBcc().contains("bcc2@example.com"));
  }

  @Test
  void testTotalRecipientCount() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient1@example.com")
        .to("recipient2@example.com")
        .addCc("cc@example.com")
        .addBcc("bcc@example.com")
        .subject("Test Subject")
        .body("Test Body")
        .build();

    // Then
    assertEquals(4, email.getTotalRecipientCount());
  }

  @Test
  void testHtmlEmail() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("HTML Email")
        .body("<h1>Hello</h1><p>This is HTML</p>")
        .html(true)
        .build();

    // Then
    assertTrue(email.isHtml());
  }

  @Test
  void testEmailPriority() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Urgent")
        .body("This is urgent")
        .priority(Priority.URGENT)
        .build();

    // Then
    assertEquals(Priority.URGENT, email.getPriority());
  }

  @Test
  void testEmailWithAttachments() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Email with attachments")
        .body("Please find attachments")
        .addAttachment("/path/to/file1.pdf")
        .addAttachment("/path/to/file2.docx")
        .build();

    // Then
    assertTrue(email.hasAttachments());
    assertEquals(2, email.getAttachments().size());
    assertTrue(email.getAttachments().contains("/path/to/file1.pdf"));
    assertTrue(email.getAttachments().contains("/path/to/file2.docx"));
  }

  @Test
  void testEmailWithMultipleAttachments() {
    // Given
    List<String> attachments = Arrays.asList(
        "/path/to/file1.pdf",
        "/path/to/file2.docx",
        "/path/to/image.jpg"
    );

    // When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Multiple attachments")
        .body("Files attached")
        .attachments(attachments)
        .build();

    // Then
    assertEquals(3, email.getAttachments().size());
  }

  @Test
  void testEmailWithoutAttachments() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("No attachments")
        .body("No files attached")
        .build();

    // Then
    assertFalse(email.hasAttachments());
    assertTrue(email.getAttachments().isEmpty());
  }

  @Test
  void testSendEmail() {
    // Given
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test Email")
        .body("Test Body")
        .build();

    // When
    boolean sent = email.send();

    // Then
    assertTrue(sent);
  }

  @Test
  void testCreatedAt() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test")
        .body("Test")
        .build();

    // Then
    assertNotNull(email.getCreatedAt());
  }

  @Test
  void testMissingFromThrowsException() {
    // Given
    EmailMessage.Builder builder = EmailMessage.builder()
        .to("recipient@example.com")
        .subject("Test")
        .body("Test");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testInvalidFromEmailThrowsException() {
    // Given
    EmailMessage.Builder builder = EmailMessage.builder()
        .from("invalid-email")
        .to("recipient@example.com")
        .subject("Test")
        .body("Test");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testMissingToThrowsException() {
    // Given
    EmailMessage.Builder builder = EmailMessage.builder()
        .from("sender@example.com")
        .subject("Test")
        .body("Test");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testInvalidToEmailThrowsException() {
    // Given
    EmailMessage.Builder builder = EmailMessage.builder()
        .from("sender@example.com")
        .to("invalid-email")
        .subject("Test")
        .body("Test");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testMissingSubjectThrowsException() {
    // Given
    EmailMessage.Builder builder = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .body("Test");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testMissingBodyThrowsException() {
    // Given
    EmailMessage.Builder builder = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testImmutableToList() {
    // Given
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test")
        .body("Test")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      email.getTo().add("new@example.com");
    });
  }

  @Test
  void testImmutableCcList() {
    // Given
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .addCc("cc@example.com")
        .subject("Test")
        .body("Test")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      email.getCc().add("new@example.com");
    });
  }

  @Test
  void testImmutableBccList() {
    // Given
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .addBcc("bcc@example.com")
        .subject("Test")
        .body("Test")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      email.getBcc().add("new@example.com");
    });
  }

  @Test
  void testImmutableAttachments() {
    // Given
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test")
        .body("Test")
        .addAttachment("/path/to/file.pdf")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      email.getAttachments().add("/new/file.pdf");
    });
  }

  @Test
  void testToString() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient@example.com")
        .subject("Test Email")
        .body("Test Body")
        .priority(Priority.HIGH)
        .build();

    String result = email.toString();

    // Then
    assertTrue(result.contains("sender@example.com"));
    assertTrue(result.contains("Test Email"));
    assertTrue(result.contains("HIGH"));
  }

  @Test
  void testCompleteEmail() {
    // Given & When
    EmailMessage email = EmailMessage.builder()
        .from("sender@example.com")
        .to("recipient1@example.com")
        .to("recipient2@example.com")
        .addCc("cc@example.com")
        .addBcc("bcc@example.com")
        .subject("Complete Email")
        .body("<h1>Welcome</h1>")
        .html(true)
        .priority(Priority.HIGH)
        .addAttachment("/path/to/document.pdf")
        .build();

    // Then
    assertNotNull(email);
    assertEquals("sender@example.com", email.getFrom());
    assertEquals(2, email.getTo().size());
    assertEquals(1, email.getCc().size());
    assertEquals(1, email.getBcc().size());
    assertEquals("Complete Email", email.getSubject());
    assertTrue(email.isHtml());
    assertEquals(Priority.HIGH, email.getPriority());
    assertTrue(email.hasAttachments());
    assertEquals(4, email.getTotalRecipientCount());
  }
}
