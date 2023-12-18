package com.example.demo.Models;

import com.example.demo.Enum.InteractionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userInteraction")
public class UserInteraction {
    private String id;
    private String userId;
    private String postId;
    private InteractionType type;
    private LocalDateTime timestamp;
}
