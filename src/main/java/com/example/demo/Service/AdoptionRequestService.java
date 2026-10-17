package com.example.demo.Service;

import com.example.demo.entity.AdoptionRequest;
import com.example.demo.repository.AdoptionRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class AdoptionRequestService {

    private final AdoptionRequestRepository repository;

    public AdoptionRequestService(
            AdoptionRequestRepository repository) {
        this.repository = repository;
    }

    public AdoptionRequest createRequest(
            Long petId,
            String petName,
            Long userId) {

        AdoptionRequest request = new AdoptionRequest();

        request.setPetId(petId);
        request.setPetName(petName);
        request.setUserId(userId);
        request.setStatus("PENDING");

        return repository.save(request);
    }
}