package com.example.demo.Configuration;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.PostDto;
import com.example.demo.Enum.PrivacyType;
import com.example.demo.Models.Comment;
import com.example.demo.Models.Post;
import com.example.demo.Repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String createPost(String userId, PostDto postDTO) {
        Post post = new Post();
        post.setUserId(userId);
        post.setText(postDTO.getText());
        post.setImages(postDTO.getImages());
        post.setVideoUrl(postDTO.getVideoUrl());
        post.setPrivacy(postDTO.getPrivacy());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public void addCommentToPost(String postId, String userId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            Comment comment = new Comment();
            comment.setUserId(userId);
            comment.setText(commentDTO.getText());
            post.getComments().add(comment);
            postRepository.save(post);
        }
    }

    public void repostPost(String postId, String userId) {
        Post originalPost = postRepository.findById(postId).orElse(null);
        if (originalPost != null) {
            Post repost = new Post();
            repost.setUserId(userId);
            repost.setText(originalPost.getText());
            repost.setImages(originalPost.getImages());
            repost.setVideoUrl(originalPost.getVideoUrl());
            postRepository.save(repost);
        }
    }
    public List<Post> getPostsByPrivacy(PrivacyType privacy) {
        return postRepository.findByPrivacy(privacy);
    }

}
