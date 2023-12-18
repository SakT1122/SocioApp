package com.example.demo.Controller;

import com.example.demo.Configuration.PostService;
import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.PostDto;
import com.example.demo.DTO.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody PostDto postDTO, Principal principal) {
        String userId = principal.getName();
        String postId = postService.createPost(userId, postDTO);
        return ResponseEntity.ok(postId);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> addCommentToPost(@PathVariable String postId, @RequestBody CommentDTO commentDTO, Principal principal) {
        String userId = principal.getName(); // Assuming user is authenticated
        postService.addCommentToPost(postId, userId, commentDTO);
        return ResponseEntity.ok("Comment added successfully");
    }

    @PostMapping("/{postId}/repost")
    public ResponseEntity<String> repostPost(@PathVariable String postId, Principal principal) {
        String userId = principal.getName(); // Assuming user is authenticated
        postService.repostPost(postId, userId);
        return ResponseEntity.ok("Post reposted successfully");
    }
}
