package com.prosoft.repository;

import com.prosoft.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PersonRepository {
    private final ConcurrentHashMap<Long, Person> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public List<Person> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Person findById(Long id) {
        return storage.get(id);
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(idGenerator.incrementAndGet());
        }
        storage.put(person.getId(), person);
        return person;
    }

    public Person update(Long id, Person person) {
        if (storage.containsKey(id)) {
            person.setId(id);
            storage.put(id, person);
            return person;
        }
        return null;
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }
}
