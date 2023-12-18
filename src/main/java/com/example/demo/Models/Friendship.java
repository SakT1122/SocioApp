package com.example.demo.Models;

import com.example.demo.Enum.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "friendship")
public class Friendship {
    private String id;
    private String requesterId;
    private String requestedId;
    private FriendshipStatus status;

}
