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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * UserDTO 테스트.
 */
class UserDTOTest {

  @Test
  void testBasicUserCreation() {
    // Given & When
    UserDTO user = UserDTO.builder()
        .username("john.doe")
        .email("john@example.com")
        .build();

    // Then
    assertNotNull(user);
    assertEquals("john.doe", user.getUsername());
    assertEquals("john@example.com", user.getEmail());
    assertTrue(user.isActive()); // 기본값
  }

  @Test
  void testFullUserCreation() {
    // Given
    LocalDate birthDate = LocalDate.of(1990, 1, 15);

    // When
    UserDTO user = UserDTO.builder()
        .id(1L)
        .username("john.doe")
        .email("john@example.com")
        .firstName("John")
        .lastName("Doe")
        .age(30)
        .phoneNumber("010-1234-5678")
        .address("123 Main St, Seoul")
        .birthDate(birthDate)
        .active(true)
        .addRole("USER")
        .addRole("ADMIN")
        .build();

    // Then
    assertEquals(1L, user.getId());
    assertEquals("john.doe", user.getUsername());
    assertEquals("john@example.com", user.getEmail());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals("John Doe", user.getFullName());
    assertEquals(30, user.getAge());
    assertEquals("010-1234-5678", user.getPhoneNumber());
    assertEquals("123 Main St, Seoul", user.getAddress());
    assertEquals(birthDate, user.getBirthDate());
    assertTrue(user.isActive());
    assertEquals(2, user.getRoles().size());
    assertTrue(user.hasRole("USER"));
    assertTrue(user.hasRole("ADMIN"));
  }

  @Test
  void testFullNameWithoutFirstAndLastName() {
    // Given & When
    UserDTO user = UserDTO.builder()
        .username("john.doe")
        .email("john@example.com")
        .build();

    // Then
    assertEquals("john.doe", user.getFullName());
  }

  @Test
  void testMultipleRoles() {
    // Given
    List<String> roles = Arrays.asList("USER", "ADMIN", "MODERATOR");

    // When
    UserDTO user = UserDTO.builder()
        .username("admin")
        .email("admin@example.com")
        .roles(roles)
        .build();

    // Then
    assertEquals(3, user.getRoles().size());
    assertTrue(user.hasRole("USER"));
    assertTrue(user.hasRole("ADMIN"));
    assertTrue(user.hasRole("MODERATOR"));
  }

  @Test
  void testHasRole() {
    // Given & When
    UserDTO user = UserDTO.builder()
        .username("john.doe")
        .email("john@example.com")
        .addRole("USER")
        .build();

    // Then
    assertTrue(user.hasRole("USER"));
    assertFalse(user.hasRole("ADMIN"));
  }

  @Test
  void testInactiveUser() {
    // Given & When
    UserDTO user = UserDTO.builder()
        .username("inactive.user")
        .email("inactive@example.com")
        .active(false)
        .build();

    // Then
    assertFalse(user.isActive());
  }

  @Test
  void testMissingUsernameThrowsException() {
    // Given
    UserDTO.Builder builder = UserDTO.builder()
        .email("test@example.com");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testEmptyUsernameThrowsException() {
    // Given
    UserDTO.Builder builder = UserDTO.builder()
        .username("")
        .email("test@example.com");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testMissingEmailThrowsException() {
    // Given
    UserDTO.Builder builder = UserDTO.builder()
        .username("john.doe");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testInvalidEmailThrowsException() {
    // Given
    UserDTO.Builder builder = UserDTO.builder()
        .username("john.doe")
        .email("invalid-email");

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testNegativeAgeThrowsException() {
    // Given
    UserDTO.Builder builder = UserDTO.builder()
        .username("john.doe")
        .email("john@example.com")
        .age(-5);

    // When & Then
    assertThrows(IllegalStateException.class, builder::build);
  }

  @Test
  void testImmutableRoles() {
    // Given
    UserDTO user = UserDTO.builder()
        .username("john.doe")
        .email("john@example.com")
        .addRole("USER")
        .build();

    // When & Then
    assertThrows(UnsupportedOperationException.class, () -> {
      user.getRoles().add("ADMIN");
    });
  }

  @Test
  void testToString() {
    // Given & When
    UserDTO user = UserDTO.builder()
        .id(1L)
        .username("john.doe")
        .email("john@example.com")
        .firstName("John")
        .lastName("Doe")
        .age(30)
        .addRole("USER")
        .build();

    String result = user.toString();

    // Then
    assertTrue(result.contains("john.doe"));
    assertTrue(result.contains("john@example.com"));
    assertTrue(result.contains("John Doe"));
  }

  @Test
  void testFluentApi() {
    // Given & When - Fluent API 테스트
    UserDTO user = UserDTO.builder()
        .username("test")
        .email("test@example.com")
        .firstName("Test")
        .lastName("User")
        .age(25)
        .phoneNumber("010-0000-0000")
        .address("Test Address")
        .addRole("USER")
        .addRole("TESTER")
        .build();

    // Then
    assertNotNull(user);
    assertEquals("test", user.getUsername());
    assertEquals(2, user.getRoles().size());
  }

  @Test
  void testBirthDate() {
    // Given
    LocalDate birthDate = LocalDate.of(1995, 5, 20);

    // When
    UserDTO user = UserDTO.builder()
        .username("john.doe")
        .email("john@example.com")
        .birthDate(birthDate)
        .build();

    // Then
    assertEquals(birthDate, user.getBirthDate());
  }
}
