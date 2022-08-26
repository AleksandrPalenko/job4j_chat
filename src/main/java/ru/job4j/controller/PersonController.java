package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Person;
import ru.job4j.model.Role;
import ru.job4j.model.UserDTO;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoleService;

import javax.validation.Valid;
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
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        validationForRegistration(person);
        person.setPassword(encoder.encode(person.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.of("ROLE_USER"));
        person.setRole(userRoles);
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        Person person = personService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Account is not found. Please, check the input data."
                        )
                );
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PutMapping("/person")
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person personById = personService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Account is not found. Please, check the input data."
                        ));
        personService.delete(personById);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<Person> patch(@Valid @RequestBody UserDTO userDTO) {
        Person current = personService.findById(Integer.parseInt(userDTO.getLogin()))
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Account is not found. Please, check the input data."
                        ));
        if (userDTO.getLogin() != null) {
            current.setLogin(userDTO.getLogin());
        }
        if (userDTO.getPassword() != null) {
            current.setPassword(userDTO.getPassword());
        }
        return ResponseEntity.status(HttpStatus.OK).body(personService.save(current));
    }

    private void validationForRegistration(@Valid Person person) {
        String login = person.getLogin();
        String password = person.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (personService.findPersonByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Account already exists");
        }
        if (!login.matches("[a-zA-Z]*")) {
            throw new IllegalArgumentException("The login must consist only of letters");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
