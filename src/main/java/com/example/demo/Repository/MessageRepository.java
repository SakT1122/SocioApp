package com.example.demo.Repository;

import com.example.demo.Models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);
    List<Message> findByReceiverIdsContaining(String receiverId);
    // Other methods for querying messages based on different criteria if needed
}