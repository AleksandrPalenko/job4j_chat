package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Message;
import ru.job4j.model.Person;
import ru.job4j.model.Room;
import ru.job4j.service.MessageService;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;
    private final RoomService roomService;

    public MessageController(MessageService messageService, PersonService personService, RoomService roomService) {
        this.messageService = messageService;
        this.personService = personService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Message> getAllMessagesFromPerson() {
           return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        Message message = messageService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Message is not found. Please, check the input data."
                        )
                );
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/room/{rId}/person/{pId}/")
    public ResponseEntity<Message> create(@RequestBody Message message, @PathVariable int rId, @PathVariable int pId) {
        if (message.getName() == null) {
            throw new NullPointerException("message must be writing");
        }
        Room room = roomService.findById(rId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Room is not found. Please, check the input data."
                )
        );
        Person person = personService.findById(pId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Person is not found. Please, check the input data."
                )
        );
        message.setPerson(person);
        room.setMessages(message.getRoom().getMessages());
        return new ResponseEntity<>(
                this.messageService.save(message),
                HttpStatus.CREATED
        );
    }

}
