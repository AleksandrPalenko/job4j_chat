package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "Id must be non null")
    private int id;
    @NotBlank(message = "Name of Role must be between 1 and 15 characters")
    @Size(min = 3, max = 15, message = "Name should be not empty")
    @Column(name = "name")
    private String name;

    public static Role of(String name) {
        Role role = new Role();
        role.name = name;
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
