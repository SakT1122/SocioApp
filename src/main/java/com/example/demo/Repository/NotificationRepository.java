package com.example.demo.Repository;

import com.example.demo.Models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserIdAndRead(String userId, boolean read);
}
