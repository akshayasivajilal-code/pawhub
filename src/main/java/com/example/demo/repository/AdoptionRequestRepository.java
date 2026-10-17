package com.example.demo.repository;

import com.example.demo.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRequestRepository
        extends JpaRepository<AdoptionRequest, Long> {
}