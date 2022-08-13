package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Person;
import ru.job4j.model.Role;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoleService;

import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public PersonController(PersonService personService, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.personService = personService;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person, @PathVariable("rId") int rId) {
        person.setPassword(encoder.encode(person.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findById(rId).get());
        person.setRole(roles);
        Person save = personService.save(person);
        return new ResponseEntity<>(
                save,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> getPerson(@PathVariable int id) {
        return personService.findById(id);
    }

    /**
    @PostMapping("/person")
    public ResponseEntity<Person> create(@RequestBody Person person,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMes = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMes.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMes.toString());
        }
        Person save = personService.save(person);
        return new ResponseEntity<>(
                save,
                HttpStatus.CREATED
        );
    }
     */

    @PutMapping("/person")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Person> personById = personService.findById(id);
        if (personById.isEmpty()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
/**
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreatedException p) {
        PersonErrorResponse response = new PersonErrorResponse(
                p.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    */
}
