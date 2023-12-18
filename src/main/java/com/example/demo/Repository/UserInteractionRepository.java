package com.example.demo.Repository;

import com.example.demo.Models.UserInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserInteractionRepository extends MongoRepository<UserInteraction, String> {
    List<UserInteraction> findByUserId(String userId);
    List<UserInteraction> findByPostId(String postId);
    List<UserInteraction> findByUserIdAndPostId(String userId, String postId);
}
