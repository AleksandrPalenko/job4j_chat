package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Message;
import ru.job4j.repository.MessageRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    @Transactional
    public Message save(Message message) {
        messageRepository.save(message);
        return message;
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
