package com.example.demo.controller;

import com.example.demo.entity.LostFound;
import com.example.demo.repository.LostFoundRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lost-found")
@CrossOrigin
public class LostFoundController {

    private final LostFoundRepository repository;

    public LostFoundController(LostFoundRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LostFound> getAllReports() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public LostFound getReport(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));
    }

    @PostMapping
    public LostFound createReport(@RequestBody LostFound report) {
        return repository.save(report);
    }

    @PutMapping("/{id}")
    public LostFound updateReport(
            @PathVariable Long id,
            @RequestBody LostFound details) {

        LostFound report = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));

        report.setPetName(details.getPetName());
        report.setType(details.getType());
        report.setBreed(details.getBreed());
        report.setLocation(details.getLocation());
        report.setStatus(details.getStatus());
        report.setDescription(details.getDescription());
        report.setImage(details.getImage());
        report.setContact(details.getContact());

        return repository.save(report);
    }

    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable Long id) {

        repository.deleteById(id);

        return "Report deleted successfully";
    }
}