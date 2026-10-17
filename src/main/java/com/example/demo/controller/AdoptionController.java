package com.example.demo.controller;

import com.example.demo.entity.Adoption;
import com.example.demo.entity.Pet;
import com.example.demo.repository.AdoptionRepository;
import com.example.demo.repository.PetRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
@CrossOrigin
public class AdoptionController {

    private final AdoptionRepository adoptionRepository;
    private final PetRepository petRepository;

    public AdoptionController(
            AdoptionRepository adoptionRepository,
            PetRepository petRepository) {

        this.adoptionRepository = adoptionRepository;
        this.petRepository = petRepository;
    }

    // Get all adoption requests
    @GetMapping
    public List<Adoption> getAllAdoptions() {
        return adoptionRepository.findAll();
    }

    // Submit an adoption request
    @PostMapping
    public Adoption createAdoption(@RequestBody AdoptionRequest request) {

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        Adoption adoption = new Adoption();

        adoption.setPet(pet);
        adoption.setApplicantName(request.getApplicantName());
        adoption.setApplicantEmail(request.getApplicantEmail());
        adoption.setApplicantPhone(request.getApplicantPhone());
        adoption.setMessage(request.getMessage());
        adoption.setStatus("PENDING");

        return adoptionRepository.save(adoption);
    }

    // Request data class
    public static class AdoptionRequest {

        private Long petId;
        private String applicantName;
        private String applicantEmail;
        private String applicantPhone;
        private String message;

        public Long getPetId() {
            return petId;
        }

        public void setPetId(Long petId) {
            this.petId = petId;
        }

        public String getApplicantName() {
            return applicantName;
        }

        public void setApplicantName(String applicantName) {
            this.applicantName = applicantName;
        }

        public String getApplicantEmail() {
            return applicantEmail;
        }

        public void setApplicantEmail(String applicantEmail) {
            this.applicantEmail = applicantEmail;
        }

        public String getApplicantPhone() {
            return applicantPhone;
        }

        public void setApplicantPhone(String applicantPhone) {
            this.applicantPhone = applicantPhone;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}