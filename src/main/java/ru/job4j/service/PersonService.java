package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Person;
import ru.job4j.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository rep;

    public PersonService(PersonRepository rep) {
        this.rep = rep;
    }

    public List<Person> findAll() {
        return new ArrayList<>(rep.findAll());
    }

    public Optional<Person> findById(int id) {
        return rep.findById(id);
    }

    @Transactional
    public Person save(Person person) {
        return rep.save(person);
    }

    public void delete(int id) {
        rep.delete(rep.findById(id).get());
    }
}
