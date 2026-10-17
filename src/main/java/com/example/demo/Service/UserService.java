package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.user;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public user registerUser(user user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        return userRepository.save(user);
    }

    public user loginUser(String email, String password) {

        user user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        throw new RuntimeException("Invalid email or password");
    }
}