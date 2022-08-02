package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.job4j.model.Message;
import ru.job4j.model.Person;
import ru.job4j.repository.MessageRepository;
import ru.job4j.repository.PersonRepository;
import ru.job4j.repository.RoomRepository;
import ru.job4j.util.PersonNotFoundException;
import ru.job4j.util.RoomNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;
    private final RoomRepository roomRepository;

    public MessageService(MessageRepository messageRepository, PersonRepository personRepository, RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    @Transactional
    public Message save(Message message) {
        personRepository.findById(message.getPerson().getId())
                .ifPresentOrElse(
                        message::setPerson,
                        () -> {
                            throw new PersonNotFoundException("This person wasn't found");
                        }
                );
        roomRepository.findById(message.getPerson().getId())
                .ifPresentOrElse(
                        message::setRoom,
                        () -> {
                            throw new RoomNotFoundException("This room wasn't found");
                        }
                );

        messageRepository.save(message);
        return message;
    }

    public void delete(int id) {
        messageRepository.delete(messageRepository.findById(id).get());
    }
}
