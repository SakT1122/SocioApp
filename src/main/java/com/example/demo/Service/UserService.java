package com.example.demo.Service;

import com.example.demo.Enum.FriendshipStatus;
import com.example.demo.Enum.Role;
import com.example.demo.Models.Follow;
import com.example.demo.Models.Friendship;
import com.example.demo.Models.User;
import com.example.demo.Repository.FollowRepository;
import com.example.demo.Repository.FriendshipRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;



@Service
public class UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final FollowRepository followRepository;


    public UserService(UserRepository userRepository, FriendshipRepository friendshipRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.followRepository = followRepository;
    }
    public User registerUser( User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent()){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setVerificationCode(generateVerificationCode());
        User savedUser = userRepository.save(user);
        sendVerificationEmail(savedUser.getEmail(), savedUser.getVerificationCode());
        return savedUser;
    }
    public boolean validatePassword(String userName, String password) {
        User user=userRepository.getByUsername(userName);
        return passwordEncoder.matches(password, user.getPassword());
    }

    public ResponseEntity<String> login(String userName, String password){
        if(!userRepository.findByUsername(userName).isPresent()){
            return ResponseEntity.badRequest().body("User not found");
        }
        if(validatePassword(userName,userRepository.getByUsername(userName).getPassword())){
            return ResponseEntity.ok("Logged In Successfully");
        }
        return ResponseEntity.badRequest().body("Password Doesn't Match");
    }
    public void sendVerificationEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("Your verification code is: " + code);
        javaMailSender.send(message);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString();
    }

    public boolean verifyEmail(String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user != null) {
            user.setEnabled(true);
            user.setVerificationCode(null); // Mark code as used
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User editAndUpdate(String username,String password, User updatedUser){
         User existing=userRepository.getByUsername(username);
         if(!passwordEncoder.matches(password, existing.getPassword())){
            return null;
         }
         existing.setBio(updatedUser.getBio());
         existing.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
         return userRepository.save(existing);
    }

    public boolean verifyAdmin(String userName){
        User user=userRepository.getByUsername(userName);
        if(user.getRole().equals(Role.ADMIN)){
            return true;
        }
        return false;
    }

    public String disableAccount(String userName, String idToDisable){
        if(!verifyAdmin(userName)){
            return "Only Admin can disable an account";
        }
        User userToDisable = userRepository.findById(idToDisable).orElse(null);
        if (userToDisable != null) {
            userToDisable.setEnabled(false);
            userRepository.save(userToDisable);
        }
        return "User Id disabled successfully";
    }

    public String deleteUser(String userName,String idToDelete) {
        if(!verifyAdmin(userName)){
            return "Only Admin can delete an account";
        }
        User userToDelete = userRepository.findById(idToDelete).orElse(null);
        if (userToDelete != null) {
            userRepository.deleteById(idToDelete);
            return "User Deleted Successfully";
        }
        return "UserId Not found";
    }

    public List<User> getAllAccounts(String userName){
        if(!verifyAdmin(userName)){
            return null;
        }
        return userRepository.findAll();
    }
    public void sendFriendRequest(String requesterId, String requestedId) {

        Friendship existingFriendship = friendshipRepository.findByRequesterIdAndRequestedId(requesterId, requestedId);
        if (existingFriendship == null) {
            Friendship friendshipRequest = new Friendship();
            friendshipRequest.setRequesterId(requesterId);
            friendshipRequest.setRequestedId(requestedId);
            friendshipRequest.setStatus(FriendshipStatus.PENDING);

            friendshipRepository.save(friendshipRequest);
        }
    }
    public void acceptFriendRequest(String friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId).orElse(null);
        if (friendship != null && friendship.getStatus() == FriendshipStatus.PENDING) {
            friendship.setStatus(FriendshipStatus.ACCEPTED);
            friendshipRepository.save(friendship);
        }
    }

    public void followUser(String followerId, String followedId) {
        Follow existingFollow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId);

        if (existingFollow == null) {
            Follow follow = new Follow();
            follow.setFollowerId(followerId);
            follow.setFollowedId(followedId);

            followRepository.save(follow);
        }
    }

}
