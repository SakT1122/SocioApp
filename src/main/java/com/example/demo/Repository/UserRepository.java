package com.example.demo.Repository;

import com.example.demo.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String > {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User getByUsername(String username);
    User findByVerificationCode(String code);
}
