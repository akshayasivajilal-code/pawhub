package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Pet;
import com.example.demo.entity.user;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(
            PetRepository petRepository,
            UserRepository userRepository) {

        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public List<Pet> getPetsByUserId(Long userId) {

        user user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return petRepository.findByUser(user);
    }
}