package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Room;
import ru.job4j.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Room> getAll() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        Room room = roomService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Room is not found. Please, check the input data."
                        )
                );
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PatchMapping("/")
    public ResponseEntity<Room> patch(@RequestBody Room room) {
        Room current = roomService.findById(room.getId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Room is not found. Please, check the input data."
                        )
                );
        if (room.getName() != null) {
            current.setName(room.getName());
        }
        return ResponseEntity.status(HttpStatus.OK).body(roomService.save(current));
    }

    @PostMapping("/addRoom")
    public ResponseEntity<Room> save(Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Name of Room is not be empty");
        }
        return new ResponseEntity<>(
                roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = roomService.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Room is not found"
        ));
        roomService.delete(room);
        return ResponseEntity.ok().build();
    }

}
