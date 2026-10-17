package com.example.demo.repository;

import com.example.demo.entity.Pet;
import com.example.demo.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByUser(user user);
}