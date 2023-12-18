package com.example.demo.Service;

import com.example.demo.Enum.InteractionType;
import com.example.demo.Models.UserInteraction;
import com.example.demo.Repository.UserInteractionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteractionTrackingService {

    private final UserInteractionRepository interactionRepository;

    public InteractionTrackingService(UserInteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    public void trackUserInteraction(String userId, String postId, InteractionType type) {
        UserInteraction interaction = new UserInteraction();
        interaction.setUserId(userId);
        interaction.setPostId(postId);
        interaction.setType(type);
        interaction.setTimestamp(LocalDateTime.now());

        interactionRepository.save(interaction);
    }

    public List<UserInteraction> getUserInteractions(String userId) {
        return interactionRepository.findByUserId(userId);
    }

    public List<UserInteraction> getPostInteractions(String postId) {
        return interactionRepository.findByPostId(postId);
    }

    public List<UserInteraction> getUserPostInteractions(String userId, String postId) {
        return interactionRepository.findByUserIdAndPostId(userId, postId);
    }

}

