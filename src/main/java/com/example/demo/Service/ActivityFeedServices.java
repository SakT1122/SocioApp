package com.example.demo.Service;

import com.example.demo.Models.Post;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.FriendshipRepository;
import com.example.demo.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityFeedServices {

    @Autowired
    private final FriendshipRepository friendshipRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;



    public ActivityFeedServices(FriendshipRepository friendshipRepository, FollowRepository followRepository, PostRepository postRepository) {
        this.friendshipRepository = friendshipRepository;
        this.followRepository = followRepository;
        this.postRepository = postRepository;
    }

    public List<Post> getActivityFeedPostsForUser(String userId) {
        List<String> friendIds = friendshipRepository.findFriendsByUserId(userId);
        List<String> followedUserIds = followRepository.findFollowedUsersByFollowerId(userId);
        return postRepository.findPostsByUserIds(friendIds, followedUserIds);
    }
}

