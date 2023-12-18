package com.example.demo.Service;

import com.example.demo.Models.Notification;
import com.example.demo.Repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByUserIdAndRead(userId, false);
    }

}

