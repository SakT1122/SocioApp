package com.example.demo.Controller;

import com.example.demo.Enum.InteractionType;
import com.example.demo.Models.UserInteraction;
import com.example.demo.Service.InteractionTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    private final InteractionTrackingService interactionService;

    public InteractionController(InteractionTrackingService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/{userId}/track/{postId}/{interactionType}")
    public ResponseEntity<String> trackUserInteraction(@PathVariable String userId, @PathVariable String postId, @PathVariable InteractionType interactionType) {
        interactionService.trackUserInteraction(userId, postId, interactionType);
        return ResponseEntity.ok("Interaction tracked successfully");
    }

    @GetMapping("/{userId}/user-interactions")
    public ResponseEntity<List<UserInteraction>> getUserInteractions(@PathVariable String userId) {
        List<UserInteraction> userInteractions = interactionService.getUserInteractions(userId);
        return ResponseEntity.ok(userInteractions);
    }

    @GetMapping("/{postId}/post-interactions")
    public ResponseEntity<List<UserInteraction>> getPostInteractions(@PathVariable String postId) {
        List<UserInteraction> postInteractions = interactionService.getPostInteractions(postId);
        return ResponseEntity.ok(postInteractions);
    }

}

