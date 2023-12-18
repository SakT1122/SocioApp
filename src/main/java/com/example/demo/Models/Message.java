package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message")
public class Message {
    private String id;
    private String senderId;
    private List<String> receiverIds;
    private String content;
    private LocalDateTime timestamp;
}
