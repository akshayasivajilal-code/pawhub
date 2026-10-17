package com.example.demo.controller;

import com.example.demo.entity.AdoptionRequest;
import com.example.demo.Service.AdoptionRequestService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adoption")
@CrossOrigin
public class AdoptionRequestController {

    private final AdoptionRequestService service;

    public AdoptionRequestController(
            AdoptionRequestService service) {
        this.service = service;
    }

    @PostMapping("/request")
    public AdoptionRequest createRequest(
            @RequestBody AdoptionRequest request) {

        return service.createRequest(
                request.getPetId(),
                request.getPetName(),
                request.getUserId()
        );
    }
}