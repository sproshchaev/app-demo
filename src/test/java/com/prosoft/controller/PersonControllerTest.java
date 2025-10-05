package com.prosoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prosoft.model.Person;
import com.prosoft.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public PersonService personService() {
            return mock(PersonService.class);
        }
    }

    @Test
    void getAllPersons_ShouldReturnJsonList() throws Exception {
        // Given
        Person person1 = new Person(1L, "Иван", 30);
        Person person2 = new Person(2L, "Петр", 25);
        List<Person> persons = Arrays.asList(person1, person2);
        when(personService.getAllPersons()).thenReturn(persons);

        // When & Then
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Иван"))
                .andExpect(jsonPath("$[1].name").value("Петр"));
    }

    @Test
    void getPersonById_WhenExists_ShouldReturnJson() throws Exception {
        // Given
        Person person = new Person(1L, "Иван", 30);
        when(personService.getPersonById(1L)).thenReturn(person);

        // When & Then
        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван"));
    }

    @Test
    void createPerson_ShouldReturnCreatedPerson() throws Exception {
        // Given
        Person input = new Person(null, "Анна", 28);
        Person saved = new Person(1L, "Анна", 28);
        when(personService.createPerson(any(Person.class))).thenReturn(saved);

        // When & Then
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Анна"));
    }

    @Test
    void updatePerson_WhenExists_ShouldReturnUpdatedPerson() throws Exception {
        // Given
        Person input = new Person(1L, "Анна", 29);
        Person updated = new Person(1L, "Анна", 29);
        when(personService.updatePerson(eq(1L), any(Person.class))).thenReturn(updated);

        // When & Then
        mockMvc.perform(put("/api/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(29));
    }

    @Test
    void deletePerson_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/persons/1"))
                .andExpect(status().isNoContent());

        verify(personService, times(1)).deletePerson(1L);
    }
}