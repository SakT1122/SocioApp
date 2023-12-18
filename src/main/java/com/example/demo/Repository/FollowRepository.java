package com.example.demo.Repository;

import com.example.demo.Models.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FollowRepository extends MongoRepository<Follow, String> {
    Follow findByFollowerIdAndFollowedId(String followerId, String followedId);
    List<String> findFollowedUsersByFollowerId(String followerId);
}
