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
package com.iluwatar.onion.infrastructure.persistence;

import com.iluwatar.onion.domain.model.Category;
import com.iluwatar.onion.domain.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryAdapterTest {

    @Mock
    private SpringDataPersonRepository springDataRepository;

    @Captor
    private ArgumentCaptor<JpaPersonEntity> entityCaptor;

    private PersonRepositoryAdapter personRepositoryAdapter;
    private JpaPersonEntity jpaPersonEntity;

    @BeforeEach
    void setUp() {
        personRepositoryAdapter = new PersonRepositoryAdapter(springDataRepository);

        // Setup test data
        var jpaCategory = new JpaCategoryEntity(1L, "Professional");
        jpaPersonEntity = new JpaPersonEntity(
                1L,
                "John",
                "Doe",
                25,
                "+1234567890",
                "john.doe@example.com",
                jpaCategory
        );
    }

    @Nested
    @DisplayName("Find By ID")
    class FindById {

        @Test
        @DisplayName("Should return Person when entity exists")
        void shouldReturnPersonWhenEntityExists() {
            // Arrange
            when(springDataRepository.findById(1L))
                    .thenReturn(Optional.of(jpaPersonEntity));

            // Act
            var result = personRepositoryAdapter.findById(1L);

            // Assert
            assertTrue(result.isPresent());
            Person person = result.get();
            assertEquals(1L, person.getId());
            assertEquals("John", person.getFirstName());
            assertEquals("Doe", person.getLastName());
            assertEquals(25, person.getAge());
            assertEquals("+1234567890", person.getPhoneNumber());
            assertEquals("john.doe@example.com", person.getEmail());
            assertEquals(1L, person.getCategory().getId());
            assertEquals("Professional", person.getCategory().getType());

            verify(springDataRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Should return empty Optional when entity not found")
        void shouldReturnEmptyWhenEntityNotFound() {
            // Arrange
            when(springDataRepository.findById(999L))
                    .thenReturn(Optional.empty());

            // Act
            var result = personRepositoryAdapter.findById(999L);

            // Assert
            assertTrue(result.isEmpty());
            verify(springDataRepository, times(1)).findById(999L);
        }
    }

    @Nested
    @DisplayName("Find By First Name")
    class FindByFirstName {

        @Test
        @DisplayName("Should return Person when entity with first name exists")
        void shouldReturnPersonWhenEntityExists() {
            // Arrange
            when(springDataRepository.findByFirstName("John"))
                    .thenReturn(Optional.of(jpaPersonEntity));

            // Act
            var result = personRepositoryAdapter.findByFirstName("John");

            // Assert
            assertTrue(result.isPresent());
            assertEquals("John", result.get().getFirstName());
            verify(springDataRepository, times(1)).findByFirstName("John");
        }

        @Test
        @DisplayName("Should return empty Optional when no entity found")
        void shouldReturnEmptyWhenNotFound() {
            // Arrange
            when(springDataRepository.findByFirstName("Unknown"))
                    .thenReturn(Optional.empty());

            // Act
            var result = personRepositoryAdapter.findByFirstName("Unknown");

            // Assert
            assertTrue(result.isEmpty());
            verify(springDataRepository, times(1)).findByFirstName("Unknown");
        }
    }

    @Nested
    @DisplayName("Find By Last Name")
    class FindByLastName {

        @Test
        @DisplayName("Should return Person when entity with last name exists")
        void shouldReturnPersonWhenEntityExists() {
            // Arrange
            when(springDataRepository.findByLastName("Doe"))
                    .thenReturn(Optional.of(jpaPersonEntity));

            // Act
            var result = personRepositoryAdapter.findByLastName("Doe");

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Doe", result.get().getLastName());
            verify(springDataRepository, times(1)).findByLastName("Doe");
        }
    }

    @Nested
    @DisplayName("Find All")
    class FindAll {

        @Test
        @DisplayName("Should return list of Persons when entities exist")
        void shouldReturnListOfPersons() {
            // Arrange
            var category2 = new JpaCategoryEntity(2L, "Personal");
            var person2 = new JpaPersonEntity(
                    2L,
                    "Jane",
                    "Smith",
                    30,
                    "+9876543210",
                    "jane@example.com",
                    category2
            );

            when(springDataRepository.findAll())
                    .thenReturn(List.of(jpaPersonEntity, person2));

            // Act
            var result = personRepositoryAdapter.findAll();

            // Assert
            assertTrue(result.isPresent());
            var persons = result.get();
            assertEquals(2, persons.size());
            assertEquals("John", persons.get(0).getFirstName());
            assertEquals("Jane", persons.get(1).getFirstName());

            verify(springDataRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no entities exist")
        void shouldReturnEmptyListWhenNoEntities() {
            // Arrange
            when(springDataRepository.findAll()).thenReturn(List.of());

            // Act
            var result = personRepositoryAdapter.findAll();

            // Assert
            assertTrue(result.isPresent());
            assertTrue(result.get().isEmpty());
            verify(springDataRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Save")
    class Save {

        @Test
        @DisplayName("Should save person and return domain model")
        void shouldSavePersonAndReturnDomainModel() {
            // Arrange
            var personToSave = new Person(
                    null, // New person without ID
                    "Jane",
                    "Smith",
                    28,
                    "+1111111111",
                    "jane@example.com",
                    new Category(2L, "Personal")
            );

            var savedCategory = new JpaCategoryEntity(2L, "Personal");
            var savedEntity = new JpaPersonEntity(
                    100L, // ID assigned by database
                    "Jane",
                    "Smith",
                    28,
                    "+1111111111",
                    "jane@example.com",
                    savedCategory
            );

            when(springDataRepository.save(any(JpaPersonEntity.class)))
                    .thenReturn(savedEntity);

            // Act
            var result = personRepositoryAdapter.save(personToSave);

            // Assert
            assertNotNull(result);
            assertEquals(100L, result.getId());
            assertEquals("Jane", result.getFirstName());
            assertEquals("Smith", result.getLastName());
            assertEquals(28, result.getAge());

            verify(springDataRepository, times(1)).save(any(JpaPersonEntity.class));
        }

        @Test
        @DisplayName("Should correctly map domain model to JPA entity")
        void shouldCorrectlyMapDomainToEntity() {
            // Arrange
            var personToSave = new Person(
                    null,
                    "Bob",
                    "Johnson",
                    35,
                    "+2222222222",
                    "bob@example.com",
                    new Category(3L, "Business")
            );

            var savedEntity = new JpaPersonEntity(
                    200L,
                    "Bob",
                    "Johnson",
                    35,
                    "+2222222222",
                    "bob@example.com",
                    new JpaCategoryEntity(3L, "Business")
            );

            when(springDataRepository.save(any(JpaPersonEntity.class)))
                    .thenReturn(savedEntity);

            // Act
            personRepositoryAdapter.save(personToSave);

            // Assert - Verify what was passed to Spring Data repository
            verify(springDataRepository).save(entityCaptor.capture());
            JpaPersonEntity capturedEntity = entityCaptor.getValue();

            assertEquals("Bob", capturedEntity.getFirstName());
            assertEquals("Johnson", capturedEntity.getLastName());
            assertEquals(35, capturedEntity.getAge());
            assertEquals("+2222222222", capturedEntity.getPhoneNumber());
            assertEquals("bob@example.com", capturedEntity.getEmail());
            assertEquals(3L, capturedEntity.getCategory().getId());
            assertEquals("Business", capturedEntity.getCategory().getType());
        }
    }

    @Nested
    @DisplayName("Delete By ID")
    class DeleteById {

        @Test
        @DisplayName("Should return true when person is successfully deleted")
        void shouldReturnTrueWhenDeleted() {
            // Arrange
            doNothing().when(springDataRepository).deleteById(1L);
            when(springDataRepository.findById(1L)).thenReturn(Optional.empty());

            // Act
            boolean result = personRepositoryAdapter.deleteById(1L);

            // Assert
            assertTrue(result);
            verify(springDataRepository, times(1)).deleteById(1L);
            verify(springDataRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Should return false when person still exists after deletion")
        void shouldReturnFalseWhenStillExists() {
            // Arrange
            doNothing().when(springDataRepository).deleteById(1L);
            when(springDataRepository.findById(1L)).thenReturn(Optional.of(jpaPersonEntity));

            // Act
            boolean result = personRepositoryAdapter.deleteById(1L);

            // Assert
            assertFalse(result);
            verify(springDataRepository, times(1)).deleteById(1L);
            verify(springDataRepository, times(1)).findById(1L);
        }
    }
}

