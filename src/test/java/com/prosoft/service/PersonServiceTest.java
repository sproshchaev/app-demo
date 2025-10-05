package com.prosoft.service;

import com.prosoft.model.Person;
import com.prosoft.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService(personRepository);
    }

    @Test
    void getAllPersons_ShouldReturnAllPersons() {
        // Given
        Person person1 = new Person(1L, "Иван", 30);
        Person person2 = new Person(2L, "Петр", 25);
        List<Person> persons = Arrays.asList(person1, person2);
        when(personRepository.findAll()).thenReturn(persons);

        // When
        List<Person> result = personService.getAllPersons();

        // Then
        assertEquals(2, result.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void getPersonById_WhenExists_ShouldReturnPerson() {
        // Given
        Person person = new Person(1L, "Иван", 30);
        when(personRepository.findById(1L)).thenReturn(person);

        // When
        Person result = personService.getPersonById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Иван", result.getName());
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void createPerson_ShouldSaveAndReturnPerson() {
        // Given
        Person person = new Person(null, "Анна", 28);
        Person saved = new Person(1L, "Анна", 28);
        when(personRepository.save(any(Person.class))).thenReturn(saved);

        // When
        Person result = personService.createPerson(person);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void updatePerson_WhenExists_ShouldReturnUpdatedPerson() {
        // Given
        Person person = new Person(1L, "Анна", 29);
        Person updated = new Person(1L, "Анна", 29);
        when(personRepository.update(1L, person)).thenReturn(updated);

        // When
        Person result = personService.updatePerson(1L, person);

        // Then
        assertNotNull(result);
        assertEquals(29, result.getAge());
        verify(personRepository, times(1)).update(1L, person);
    }

    @Test
    void deletePerson_ShouldCallRepositoryDelete() {
        // When
        personService.deletePerson(1L);

        // Then
        verify(personRepository, times(1)).deleteById(1L);
    }
}