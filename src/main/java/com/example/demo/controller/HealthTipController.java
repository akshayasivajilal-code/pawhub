package com.example.demo.controller;

import com.example.demo.entity.HealthTip;
import com.example.demo.Service.HealthTipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-tips")
@CrossOrigin
public class HealthTipController {

    private final HealthTipService healthTipService;

    public HealthTipController(HealthTipService healthTipService) {
        this.healthTipService = healthTipService;
    }

    @GetMapping
    public List<HealthTip> getAllTips() {
        return healthTipService.getAllTips();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthTip> getTipById(@PathVariable Long id) {

        HealthTip tip =
                healthTipService.getTipById(id);

        if (tip == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tip);
    }

    @PostMapping
    public HealthTip createTip(
            @RequestBody HealthTip healthTip) {

        return healthTipService.saveTip(healthTip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTip(
            @PathVariable Long id) {

        healthTipService.deleteTip(id);

        return ResponseEntity.noContent().build();
    }
}