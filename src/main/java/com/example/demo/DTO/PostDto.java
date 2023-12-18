package com.example.demo.DTO;

import com.example.demo.Enum.PrivacyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String text;
    private List<String> images; // List of image URLs
    private String videoUrl;
    private PrivacyType privacy;

    // Constructors, getters, setters, etc.


}
