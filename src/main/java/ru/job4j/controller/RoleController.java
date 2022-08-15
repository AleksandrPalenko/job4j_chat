package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Role;
import ru.job4j.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @PostMapping("/addRole")
    public ResponseEntity<Role> save(Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("Name of Role is not be empty");
        }
        return new ResponseEntity<>(
                roleService.save(role),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        Role role = roleService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Role is not found. Please, check the input data."
                        )
                );
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
