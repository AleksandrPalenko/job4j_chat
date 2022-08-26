package ru.job4j.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name")
    @NotNull(message = "Id must be non null")
    private int id;
    @NotBlank(message = "Name of Room must be between 1 and 15 characters")
    @Size(min = 3, max = 15, message = "Name should be not empty")
    @Column(name = "name", insertable = false, updatable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private List<Person> listOfPersons = new ArrayList<>();
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Message> messages = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
