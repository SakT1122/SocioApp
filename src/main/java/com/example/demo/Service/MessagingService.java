package com.example.demo.Service;

import com.example.demo.Models.Message;
import com.example.demo.Repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class MessagingService {

    private final MessageRepository messageRepository;

    public MessagingService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendDirectMessage(String senderId, String receiverId, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverIds(Collections.singletonList(receiverId));
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    public void sendGroupMessage(String senderId, List<String> receiverIds, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverIds(receiverIds);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    public List<Message> getDirectMessages(String senderId, String receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    public List<Message> getGroupMessages(String receiverId) {
        return messageRepository.findByReceiverIdsContaining(receiverId);
    }

}