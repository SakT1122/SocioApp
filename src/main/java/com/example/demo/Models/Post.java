package com.example.demo.Models;

import com.example.demo.Enum.PrivacyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {
    private String id;
    private String userId; // Creator's user ID
    private String text;
    private List<String> images; // List of image URLs
    private String videoUrl;
    private List<Comment> comments; // List of comments
    private PrivacyType privacy;

}
