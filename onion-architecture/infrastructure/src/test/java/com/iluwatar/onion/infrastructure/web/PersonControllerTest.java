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
package com.iluwatar.onion.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.onion.application.dto.PersonResponse;
import com.iluwatar.onion.application.dto.SavePersonCommand;
import com.iluwatar.onion.application.usecase.GetPersonUseCase;
import com.iluwatar.onion.application.usecase.SavePersonUseCase;
import com.iluwatar.onion.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SavePersonUseCase savePersonUseCase;

    @MockitoBean
    private GetPersonUseCase getPersonUseCase;

    @Nested
    @DisplayName("GET /api/persons/{id}")
    class GetPersonById {

        @Test
        @DisplayName("Should return person when person exists")
        void shouldReturnPersonWhenExists() throws Exception {
            // Arrange
            var response = new PersonResponse(
                    1L,
                    "John",
                    "Doe",
                    25,
                    "+1234567890",
                    "john.doe@example.com",
                    1L,
                    "Professional"
            );

            when(getPersonUseCase.execute(1L)).thenReturn(response);

            // Act & Assert
            mockMvc.perform(get("/api/persons/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.firstName").value("John"))
                    .andExpect(jsonPath("$.lastName").value("Doe"))
                    .andExpect(jsonPath("$.age").value(25))
                    .andExpect(jsonPath("$.phoneNumber").value("+1234567890"))
                    .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                    .andExpect(jsonPath("$.categoryId").value(1))
                    .andExpect(jsonPath("$.categoryType").value("Professional"));

            verify(getPersonUseCase, times(1)).execute(1L);
        }

        @Test
        @DisplayName("Should handle different person IDs")
        void shouldHandleDifferentPersonIds() throws Exception {
            // Arrange
            var response = new PersonResponse(
                    42L,
                    "Jane",
                    "Smith",
                    30,
                    "+9876543210",
                    "jane@example.com",
                    2L,
                    "Personal"
            );

            when(getPersonUseCase.execute(42L)).thenReturn(response);

            // Act & Assert
            mockMvc.perform(get("/api/persons/42")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(42))
                    .andExpect(jsonPath("$.firstName").value("Jane"));

            verify(getPersonUseCase, times(1)).execute(42L);
        }
    }

    @Nested
    @DisplayName("GET /api/persons")
    class GetAllPersons {

        @Test
        @DisplayName("Should return list of persons")
        void shouldReturnListOfPersons() throws Exception {
            // Arrange
            var person1 = new PersonResponse(
                    1L, "John", "Doe", 25, "+1234567890",
                    "john@example.com", 1L, "Professional"
            );
            var person2 = new PersonResponse(
                    2L, "Jane", "Smith", 30, "+9876543210",
                    "jane@example.com", 2L, "Personal"
            );

            var persons = List.of(person1, person2);
            when(getPersonUseCase.executeAll()).thenReturn(persons);

            // Act & Assert
            mockMvc.perform(get("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].firstName").value("John"))
                    .andExpect(jsonPath("$[1].id").value(2))
                    .andExpect(jsonPath("$[1].firstName").value("Jane"));

            verify(getPersonUseCase, times(1)).executeAll();
        }

        @Test
        @DisplayName("Should return empty list when no persons exist")
        void shouldReturnEmptyListWhenNoPersons() throws Exception {
            // Arrange
            when(getPersonUseCase.executeAll()).thenReturn(List.of());

            // Act & Assert
            mockMvc.perform(get("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(0)));

            verify(getPersonUseCase, times(1)).executeAll();
        }

        @Test
        @DisplayName("Should handle large list of persons")
        void shouldHandleLargeListOfPersons() throws Exception {
            // Arrange
            var persons = List.of(
                    new PersonResponse(1L, "Person1", "Last1", 25, "+1", "p1@example.com", 1L, "Cat1"),
                    new PersonResponse(2L, "Person2", "Last2", 26, "+2", "p2@example.com", 1L, "Cat1"),
                    new PersonResponse(3L, "Person3", "Last3", 27, "+3", "p3@example.com", 1L, "Cat1")
            );

            when(getPersonUseCase.executeAll()).thenReturn(persons);

            // Act & Assert
            mockMvc.perform(get("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)));

            verify(getPersonUseCase, times(1)).executeAll();
        }
    }

    @Nested
    @DisplayName("POST /api/persons")
    class SavePerson {

        @Test
        @DisplayName("Should create person with valid data")
        void shouldCreatePersonWithValidData() throws Exception {
            // Arrange
            var command = new SavePersonCommand(
                    "John",
                    "Doe",
                    25,
                    "+1234567890",
                    "john.doe@example.com",
                    "123 Main St",
                    1L,
                    "Professional"
            );

            var response = new PersonResponse(
                    1L,
                    "John",
                    "Doe",
                    25,
                    "+1234567890",
                    "john.doe@example.com",
                    1L,
                    "Professional"
            );

            when(savePersonUseCase.execute(any(SavePersonCommand.class))).thenReturn(response);

            // Act & Assert
            mockMvc.perform(post("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.firstName").value("John"))
                    .andExpect(jsonPath("$.lastName").value("Doe"))
                    .andExpect(jsonPath("$.age").value(25))
                    .andExpect(jsonPath("$.phoneNumber").value("+1234567890"))
                    .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                    .andExpect(jsonPath("$.categoryId").value(1))
                    .andExpect(jsonPath("$.categoryType").value("Professional"));

            verify(savePersonUseCase, times(1)).execute(any(SavePersonCommand.class));
        }

        @Test
        @DisplayName("Should handle validation errors from use case")
        void shouldPropagateValidationErrors() throws Exception {
            // Arrange
            var command = new SavePersonCommand(
                    "Young",
                    "Person",
                    17, // Invalid age
                    "+1234567890",
                    "young@example.com",
                    "123 Main St",
                    1L,
                    "Student"
            );

            when(savePersonUseCase.execute(any(SavePersonCommand.class)))
                    .thenThrow(new DomainException("Age cannot be less than 18"));

            // Act & Assert
            mockMvc.perform(post("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().is4xxClientError());

            verify(savePersonUseCase, times(1)).execute(any(SavePersonCommand.class));
        }

        @Test
        @DisplayName("Should handle malformed JSON")
        void shouldHandleMalformedJson() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{invalid json}"))
                    .andExpect(status().isBadRequest());

            verify(savePersonUseCase, never()).execute(any(SavePersonCommand.class));
        }

        @Test
        @DisplayName("Should accept all required fields in command")
        void shouldAcceptAllRequiredFields() throws Exception {
            // Arrange
            var command = new SavePersonCommand(
                    "Complete",
                    "Person",
                    30,
                    "+1111111111",
                    "complete@example.com",
                    "456 Oak Ave",
                    5L,
                    "Business"
            );

            var response = new PersonResponse(
                    10L,
                    "Complete",
                    "Person",
                    30,
                    "+1111111111",
                    "complete@example.com",
                    5L,
                    "Business"
            );

            when(savePersonUseCase.execute(any(SavePersonCommand.class))).thenReturn(response);

            // Act & Assert
            mockMvc.perform(post("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.categoryType").value("Business"));

            verify(savePersonUseCase, times(1)).execute(any(SavePersonCommand.class));
        }
    }

    @Nested
    @DisplayName("Controller Integration")
    class ControllerIntegration {

        @Test
        @DisplayName("Should handle multiple requests sequentially")
        void shouldHandleMultipleRequestsSequentially() throws Exception {
            // Arrange
            var getResponse = new PersonResponse(
                    1L, "John", "Doe", 25, "+1234567890",
                    "john@example.com", 1L, "Professional"
            );

            var savePersonCommand = new SavePersonCommand(
                    "Jane", "Smith", 30, "+9876543210",
                    "jane@example.com", "456 Oak", 2L, "Personal"
            );

            var savePersonResponse = new PersonResponse(
                    2L, "Jane", "Smith", 30, "+9876543210",
                    "jane@example.com", 2L, "Personal"
            );

            when(getPersonUseCase.execute(1L)).thenReturn(getResponse);
            when(savePersonUseCase.execute(any(SavePersonCommand.class))).thenReturn(savePersonResponse);

            // Act & Assert - GET request
            mockMvc.perform(get("/api/persons/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("John"));

            // Act & Assert - POST request
            mockMvc.perform(post("/api/persons")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(savePersonCommand)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value("Jane"));

            verify(getPersonUseCase, times(1)).execute(1L);
            verify(savePersonUseCase, times(1)).execute(any(SavePersonCommand.class));
        }
    }
}

