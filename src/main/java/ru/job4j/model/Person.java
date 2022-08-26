package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null")
    private int id;
    @NotBlank(message = "login must be empty")
    @Size(min = 1, max = 15, message
            = "Message must be between 1 and 15 characters")
    @Column(name = "login")
    private String login;
    @NotBlank(message = "password must be non null")
    @Size(min = 1, max = 200, message
            = "Password must be between 1 and 8 characters")
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<Role> role = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id && login.equals(person.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
