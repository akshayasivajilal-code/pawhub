package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.entity.user;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin
public class PetController {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetController(
            PetRepository petRepository,
            UserRepository userRepository) {

        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    // Get all pets
    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // Get pets for a specific user
    @GetMapping("/user/{userId}")
    public List<Pet> getPetsByUser(@PathVariable Long userId) {

        user user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return petRepository.findByUser(user);
    }

    // Get one pet
    @GetMapping("/{id}")
    public Optional<Pet> getPetById(@PathVariable Long id) {
        return petRepository.findById(id);
    }

    // Add a new pet
    @PostMapping
    public Pet addPet(
            @RequestParam Long userId,
            @RequestBody Pet pet) {

        user user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        pet.setUser(user);

        return petRepository.save(pet);
    }

    // Update a pet
    @PutMapping("/{id}")
    public Pet updatePet(
            @PathVariable Long id,
            @RequestBody Pet petDetails) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pet not found"));

        pet.setName(petDetails.getName());
        pet.setType(petDetails.getType());
        pet.setBreed(petDetails.getBreed());
        pet.setAge(petDetails.getAge());
        pet.setGender(petDetails.getGender());
        pet.setDescription(petDetails.getDescription());
        pet.setImage(petDetails.getImage());
        pet.setStatus(petDetails.getStatus());

        return petRepository.save(pet);
    }

    // Delete a pet
    @DeleteMapping("/{id}")
    public String deletePet(@PathVariable Long id) {

        petRepository.deleteById(id);

        return "Pet deleted successfully";
    }
}