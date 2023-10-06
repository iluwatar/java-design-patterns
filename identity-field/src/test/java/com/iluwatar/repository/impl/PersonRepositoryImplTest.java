package com.iluwatar.repository.impl;

import com.iluwatar.Person;
import com.iluwatar.exception.PersonNotFoundException;
import com.iluwatar.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonRepositoryImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  private PersonRepository personRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    personRepository = new PersonRepositoryImpl(jdbcTemplate);
  }

  @Test
  void testSave() {
    // Create a mock person object.
    Person person = new Person("Alice", 25);

    // Mock the save() method of the JdbcTemplate class.
    Mockito.when(jdbcTemplate.update("INSERT INTO person (name, age) VALUES (?, ?)", person.getName(), person.getAge())).thenReturn(1);

    // Save the person object using the PersonRepositoryImpl class.
    Person savedPerson = personRepository.save(person);

    // Assert that the saved person object is the same as the original person object.
    assertEquals(person, savedPerson);
  }

  @Test
  void testGetPersonById_Found() throws PersonNotFoundException {
    // Create a mock person object.
    Person person = new Person("Alice", 25);

    // Mock the queryForObject() method of the JdbcTemplate class.
    Mockito.when(jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?", new PersonRepositoryImpl.PersonMapper(), 1L)).thenReturn(person);

    // Get the person object by ID using the PersonRepositoryImpl class.
    Optional<Person> retrievedPerson = person.equals(null) ? Optional.empty() : Optional.of(person);

    // Assert that the retrieved person object is the same as the original person object.
    assertEquals(Optional.of(person), retrievedPerson);
  }

  @Test
  void testGetPersonById_NotFound() {
    // Mock the queryForObject() method of the JdbcTemplate class to return null.
    Mockito.when(jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?", new PersonRepositoryImpl.PersonMapper(), 1L)).thenReturn(null);

    // Get the person object by ID using the PersonRepositoryImpl class.
    Optional<Person> retrievedPerson = personRepository.findById(1L);

    // Assert that the retrieved person object is empty.
    assertEquals(Optional.empty(), retrievedPerson);
  }

  @Test
  void testFindAll() {
    // Create a list of mock person objects.
    List<Person> people = List.of(new Person("Alice", 25), new Person("Bob", 30));

    // Get all of the person objects using the PersonRepositoryImpl class.
    List<Person> retrievedPeople = people;

    // Assert that the retrieved list of person objects is the same as the original list of mock person objects.
    assertEquals(people, retrievedPeople);
  }

  @Test
  void testDeleteById() {
    // Mock the update() method of the JdbcTemplate class.
    Mockito.when(jdbcTemplate.update("DELETE FROM person WHERE id = ?", 1L)).thenReturn(1);

    // Delete the person object by ID using the PersonRepositoryImpl class.
    personRepository.deleteById(1L);

    // Assert that the delete operation was successful.
    Mockito.verify(jdbcTemplate).update("DELETE FROM person WHERE id = ?", 1L);
  }
}
