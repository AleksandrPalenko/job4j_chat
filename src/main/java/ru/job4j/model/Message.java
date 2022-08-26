package ru.job4j.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "Id must be non null")
    private int id;
    @NotEmpty(message = "text should not be empty ")
    @Size(min = 1, max = 200, message
            = "Message must be between 1 and 200 characters")
    @Column(name = "text")
    private String name;
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Person person;

    public static Message of(int id, String name) {
        Message message = new Message();
        message.id = id;
        message.name = name;
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id && name.equals(message.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
