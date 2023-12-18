package com.example.demo.Repository;

import com.example.demo.Enum.PrivacyType;
import com.example.demo.Models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByPrivacy(PrivacyType privacy);
    List<Post> findPostsByUserIds(List<String> friendIds, List<String> followedUserIds);

}
