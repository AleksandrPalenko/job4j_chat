package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.Room;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {

    List<Room> findAll();
}
