package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Room;
import ru.job4j.repository.RoomRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository rep;

    public RoomService(RoomRepository rep) {
        this.rep = rep;
    }

    public List<Room> getAllRooms() {
        return (List<Room>) rep.findAll();
    }

    public Optional<Room> findById(int id) {
        return rep.findById(id);
    }

    @Transactional
    public Room save(Room room) {
        return rep.save(room);
    }

    public void delete(Room room) {
        rep.delete(room);
    }
}
