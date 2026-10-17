package com.example.demo.Service;

import com.example.demo.entity.HealthTip;
import com.example.demo.repository.HealthTipRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthTipService {

    private final HealthTipRepository healthTipRepository;

    public HealthTipService(
            HealthTipRepository healthTipRepository) {

        this.healthTipRepository = healthTipRepository;
    }

    /*
     * Add default health tips automatically
     * when database is empty.
     */
    @PostConstruct
    public void addDefaultHealthTips() {

        if (healthTipRepository.count() == 0) {

            healthTipRepository.save(
                    new HealthTip(
                            "Hydration",
                            "Always keep fresh drinking water available for your pet.",
                            "💧"
                    )
            );

            healthTipRepository.save(
                    new HealthTip(
                            "Healthy Food",
                            "Feed a balanced diet suitable for your pet.",
                            "🥩"
                    )
            );

            healthTipRepository.save(
                    new HealthTip(
                            "Exercise",
                            "Daily walks and playtime keep pets active and happy.",
                            "🏃"
                    )
            );

            healthTipRepository.save(
                    new HealthTip(
                            "Regular Checkups",
                            "Visit a veterinarian for routine health checks.",
                            "🩺"
                    )
            );
        }
    }

    public List<HealthTip> getAllTips() {

        return healthTipRepository.findAll();
    }

    public HealthTip getTipById(Long id) {

        return healthTipRepository
                .findById(id)
                .orElse(null);
    }

    public HealthTip saveTip(HealthTip healthTip) {

        return healthTipRepository.save(healthTip);
    }

    public void deleteTip(Long id) {

        healthTipRepository.deleteById(id);
    }
}