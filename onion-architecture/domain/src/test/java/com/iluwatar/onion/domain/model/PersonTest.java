/*
 *
 *  * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *  *
 *  * The MIT License
 *  * Copyright © 2014-2022 Ilkka Seppälä
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */
package com.iluwatar.onion.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.iluwatar.onion.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PersonTest {

  private final Category validCategory = new Category(1L, "Professional");

  @Nested
  @DisplayName("Person Creation - Valid Cases")
  class ValidPersonCreation {

    @Test
    @DisplayName("Should create person with valid data")
    void shouldCreatePersonWithValidData() {
      // Arrange & Act
      var person =
          new Person(1L, "John", "Doe", 25, "+1234567890", "john.doe@example.com", validCategory);

      // Assert
      assertNotNull(person);
      assertEquals(1L, person.getId());
      assertEquals("John", person.getFirstName());
      assertEquals("Doe", person.getLastName());
      assertEquals(25, person.getAge());
      assertEquals("+1234567890", person.getPhoneNumber());
      assertEquals("john.doe@example.com", person.getEmail());
      assertEquals(validCategory, person.getCategory());
    }

    @Test
    @DisplayName("Should create person with minimum valid age (18)")
    void shouldCreatePersonWithMinimumValidAge() {
      // Arrange & Act
      var person =
          new Person(
              null,
              "Jane",
              "Smith",
              18, // minimum valid age
              "+9876543210",
              "jane@example.com",
              validCategory);

      // Assert
      assertEquals(18, person.getAge());
    }
  }

  @Nested
  @DisplayName("Person Creation - Invalid Cases")
  class InvalidPersonCreation {

    @Test
    @DisplayName("Should throw exception when first name is null")
    void shouldThrowExceptionWhenFirstNameIsNull() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () ->
                  new Person(
                      1L, null, "Doe", 25, "+1234567890", "john@example.com", validCategory));
      assertTrue(exception.getMessage().contains("First name and last name cannot be null"));
    }

    @Test
    @DisplayName("Should throw exception when last name is null")
    void shouldThrowExceptionWhenLastNameIsNull() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () ->
                  new Person(
                      1L, "John", null, 25, "+1234567890", "john@example.com", validCategory));
      assertTrue(exception.getMessage().contains("First name and last name cannot be null"));
    }

    @Test
    @DisplayName("Should throw exception when age is less than 18")
    void shouldThrowExceptionWhenAgeIsLessThan18() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () ->
                  new Person(
                      1L, "John", "Doe", 17, "+1234567890", "john@example.com", validCategory));
      assertTrue(exception.getMessage().contains("Age cannot be less than 18"));
    }

    @Test
    @DisplayName("Should throw exception when phone number is null")
    void shouldThrowExceptionWhenPhoneIsNull() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () -> new Person(1L, "John", "Doe", 25, null, "john@example.com", validCategory));
      assertTrue(exception.getMessage().contains("Phone number cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw exception when phone number is empty")
    void shouldThrowExceptionWhenPhoneIsEmpty() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () -> new Person(1L, "John", "Doe", 25, "", "john@example.com", validCategory));
      assertTrue(exception.getMessage().contains("Phone number cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () -> new Person(1L, "John", "Doe", 25, "+1234567890", null, validCategory));
      assertTrue(exception.getMessage().contains("Email cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw exception when email is empty")
    void shouldThrowExceptionWhenEmailIsEmpty() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () -> new Person(1L, "John", "Doe", 25, "+1234567890", "", validCategory));
      assertTrue(exception.getMessage().contains("Email cannot be null or empty"));
    }

    @Test
    @DisplayName("Should throw exception when category is null")
    void shouldThrowExceptionWhenCategoryIsNull() {
      // Act & Assert
      var exception =
          assertThrows(
              DomainException.class,
              () -> new Person(1L, "John", "Doe", 25, "+1234567890", "john@example.com", null));
      assertTrue(exception.getMessage().contains("Category cannot be null or empty"));
    }
  }
}
