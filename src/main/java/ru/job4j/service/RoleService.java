package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Role;
import ru.job4j.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository rep;

    public RoleService(RoleRepository rep) {
        this.rep = rep;
    }

    public List<Role> findAll() {
        return (List<Role>) rep.findAll();
    }

    public Optional<Role> findById(int id) {
        return rep.findById(id);
    }

    @Transactional
    public Role save(Role role) {
        return rep.save(role);
    }

    public void delete(int id) {
        rep.delete(rep.findById(id).get());
    }
}
