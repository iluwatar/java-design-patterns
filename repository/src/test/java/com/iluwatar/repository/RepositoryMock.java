package com.iluwatar.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class RepositoryMock {

    @Test
    public void testSave(){
        PersonRepository personRepository = Mockito.mock(PersonRepository.class);
        Person newPerson = new Person("Jana", "Sucha", 22);
        Person fakePerson = new Person("Dana", "Mokra", 66);

        personRepository.save(newPerson);

        Mockito.verify(personRepository).save(newPerson);

        when(personRepository.findByName("Jana")).thenReturn(fakePerson);
        assertEquals(personRepository.findByName("Jana"), fakePerson);
    }

}
