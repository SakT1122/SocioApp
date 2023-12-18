package com.example.demo.Repository;

import com.example.demo.Models.Friendship;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendshipRepository extends MongoRepository<Friendship,String> {

    Friendship findByRequesterIdAndRequestedId(String requesterId, String requestedId);
    List<String> findFriendsByUserId(String userId);
}
