package com.soliduslabs.homework.Problem1.controller;

import com.soliduslabs.homework.Problem1.coverter.MessageConverter;
import com.soliduslabs.homework.Problem1.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class MessagesController {
    private MessageConverter messageConverter;

    @Autowired
    public MessagesController(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }


    @PostMapping(path = "/messages")
    public String hashMessageToSHA256(@RequestBody Message message) {

        return messageConverter.convertMessageToSHA256(message);
    }

    @GetMapping(path = "/messages/{sha256Hash}")
    public Message getMessageBySHA256String(@PathVariable String sha256Hash) {
        Optional<Message> message = Optional.ofNullable(messageConverter.convertSHA256ToMessage(sha256Hash));
        if (message.isPresent()) {
            return message.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Message Not Found");
        }

    }

}
