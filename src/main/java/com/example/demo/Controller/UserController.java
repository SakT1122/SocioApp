package com.example.demo.Controller;

import com.example.demo.Models.Message;
import com.example.demo.Models.Notification;
import com.example.demo.Models.Post;
import com.example.demo.Models.User;
import com.example.demo.Service.ActivityFeedServices;
import com.example.demo.Service.MessagingService;
import com.example.demo.Service.NotificationService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private final ActivityFeedServices activityFeedService;
    private final NotificationService notificationService;
    private final MessagingService messagingService;

    public UserController(MessagingService messagingService,ActivityFeedServices activityFeedService, NotificationService notificationService) {
        this.activityFeedService = activityFeedService;
        this.notificationService = notificationService;
        this.messagingService = messagingService;
    }
    @PostMapping("add")
    public String add(){
        return "hi";
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to register since username or email already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@PathVariable String userName, @PathVariable String password){
        return userService.login(userName,password);
    }

    @PutMapping("/update")
    public ResponseEntity<String> editAndUpdate(@PathVariable String username, @PathVariable String password ,@RequestBody User updatedUser){
        User user=userService.editAndUpdate(username,password,updatedUser);
        if(user==null){
            return ResponseEntity.badRequest().body("Password doesn't match");
        }
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/disable_account")
    public String disableAccount(String userName, String userNameToDisable){
        String s=userService.disableAccount(userName,userNameToDisable);
        return s;
    }

    @PostMapping("/delete-accounts")
    public String deleteAccount(String userName, String userNameToDisable){
        String s=userService.deleteUser(userName,userNameToDisable);
        return s;
    }

    @GetMapping("/getAccounts")
    public List<User> getAllAccounts(String userName){
        return userService.getAllAccounts(userName);
    }

    @GetMapping("/{userId}/activity-feed")
    public ResponseEntity<List<Post>> getActivityFeed(@PathVariable String userId) {
        List<Post> activityFeed = activityFeedService.getActivityFeedPostsForUser(userId);
        return ResponseEntity.ok(activityFeed);
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String userId) {
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    @PostMapping("/{senderId}/messages/{receiverId}")
    public ResponseEntity<String> sendDirectMessage(@PathVariable String senderId, @PathVariable String receiverId, @RequestBody String content) {
        messagingService.sendDirectMessage(senderId, receiverId, content);
        return ResponseEntity.ok("Message sent successfully");
    }

    @PostMapping("/{senderId}/group-messages")
    public ResponseEntity<String> sendGroupMessage(@PathVariable String senderId, @RequestBody List<String> receiverIds, @RequestBody String content) {
        messagingService.sendGroupMessage(senderId, receiverIds, content);
        return ResponseEntity.ok("Group message sent successfully");
    }

    @GetMapping("/{senderId}/messages/{receiverId}")
    public ResponseEntity<List<Message>> getDirectMessages(@PathVariable String senderId, @PathVariable String receiverId) {
        List<Message> messages = messagingService.getDirectMessages(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{receiverId}/group-messages")
    public ResponseEntity<List<Message>> getGroupMessages(@PathVariable String receiverId) {
        List<Message> groupMessages = messagingService.getGroupMessages(receiverId);
        return ResponseEntity.ok(groupMessages);
    }
}
