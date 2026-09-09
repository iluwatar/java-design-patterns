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
package com.iluwatar.onion.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.iluwatar.onion.application.dto.PersonResponse;
import com.iluwatar.onion.domain.model.Category;
import com.iluwatar.onion.domain.model.Person;
import com.iluwatar.onion.domain.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPersonUseCaseTest {

  @Mock private PersonRepository personRepository;

  private GetPersonUseCase getPersonUseCase;

  @BeforeEach
  void setUp() {
    getPersonUseCase = new GetPersonUseCase(personRepository);
  }

  @Nested
  @DisplayName("Execute by ID - Happy Path")
  class ExecuteByIdHappyPath {

    @Test
    @DisplayName("Should return PersonResponse when person exists")
    void shouldReturnPersonResponseWhenPersonExists() {
      // Arrange
      var person =
          new Person(
              1L,
              "John",
              "Doe",
              25,
              "+1234567890",
              "john.doe@example.com",
              new Category(1L, "Professional"));

      when(personRepository.findById(1L)).thenReturn(Optional.of(person));

      // Act
      var response = getPersonUseCase.execute(1L);

      // Assert
      assertNotNull(response);
      assertEquals(1L, response.id());
      assertEquals("John", response.firstName());
      assertEquals("Doe", response.lastName());
      assertEquals(25, response.age());
      assertEquals("+1234567890", response.phoneNumber());
      assertEquals("john.doe@example.com", response.email());
      assertEquals(1L, response.categoryId());
      assertEquals("Professional", response.categoryType());

      verify(personRepository, times(1)).findById(1L);
    }
  }

  @Nested
  @DisplayName("Execute by ID - Error Cases")
  class ExecuteByIdErrorCases {

    @Test
    @DisplayName("Should throw RuntimeException when person not found")
    void shouldThrowExceptionWhenPersonNotFound() {
      // Arrange
      when(personRepository.findById(999L)).thenReturn(Optional.empty());

      // Act & Assert
      var exception = assertThrows(RuntimeException.class, () -> getPersonUseCase.execute(999L));
      assertTrue(exception.getMessage().contains("Person not found with id: 999"));

      verify(personRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should propagate exception when repository throws exception")
    void shouldPropagateExceptionWhenRepositoryFails() {
      // Arrange
      when(personRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));

      // Act & Assert
      var exception = assertThrows(RuntimeException.class, () -> getPersonUseCase.execute(1L));
      assertTrue(exception.getMessage().contains("Database error"));

      verify(personRepository, times(1)).findById(1L);
    }
  }

  @Nested
  @DisplayName("Execute All - Happy Path")
  class ExecuteAllHappyPath {

    @Test
    @DisplayName("Should return list of PersonResponses")
    void shouldReturnListOfPersonResponses() {
      // Arrange
      var person1 =
          new Person(
              1L,
              "John",
              "Doe",
              30,
              "+9876543255",
              "john.doe@example.com",
              new Category(2L, "Personal"));

      var person2 =
          new Person(
              2L,
              "Jane",
              "Smith",
              25,
              "+9876543210",
              "jane.smith@example.com",
              new Category(2L, "Personal"));

      when(personRepository.findAll()).thenReturn(Optional.of(List.of(person1, person2)));

      // Act
      var responses = getPersonUseCase.executeAll();

      // Assert
      assertNotNull(responses);
      assertEquals(2, responses.size());

      PersonResponse response1 = responses.get(0);
      assertEquals(1L, response1.id());
      assertEquals("John", response1.firstName());
      assertEquals("Doe", response1.lastName());

      PersonResponse response2 = responses.get(1);
      assertEquals(2L, response2.id());
      assertEquals("Jane", response2.firstName());
      assertEquals("Smith", response2.lastName());

      verify(personRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no persons found")
    void shouldReturnEmptyListWhenNoPersonsFound() {
      // Arrange
      when(personRepository.findAll()).thenReturn(Optional.of(List.of()));

      // Act
      List<PersonResponse> responses = getPersonUseCase.executeAll();

      // Assert
      assertNotNull(responses);
      assertTrue(responses.isEmpty());

      verify(personRepository, times(1)).findAll();
    }
  }
}
