package com.iluwatar.services;

import com.iluwatar.Person;
import com.iluwatar.exception.PersonNotFoundException;
import com.iluwatar.repository.PersonRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonServiceTest {

  @Mock
  private PersonRepository personRepository;

  private PersonService personService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    personService = new PersonService(personRepository);
  }

  @Test
  void testCreatePerson() {
    Person person = new Person("Alice", 25);

    Mockito.when(personRepository.save(person)).thenReturn(person);

    Person createdPerson = personService.createPerson("Alice", 25);

    assertEquals(person.getName(),
            createdPerson.getName());
  }

  @Test
  void testGetPersonById_Found() throws PersonNotFoundException {
    Person person = new Person("Alice", 25);

    Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));

    Person retrievedPerson = personService.getPersonById(1L);

    assertEquals(person, retrievedPerson);
  }

  @Test
  void testGetPersonById_NotFound() {
    Mockito.when(personRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(1L));
  }

  @Test
  void testUpdatePerson() {
    Person person = new Person("Alice", 25);

    personService.updatePerson(person);

    Mockito.verify(personRepository).save(person);
  }

  @Test
  void testDeletePersonById() {
    personService.deletePersonById(1L);

    Mockito.verify(personRepository).deleteById(1L);
  }
}
