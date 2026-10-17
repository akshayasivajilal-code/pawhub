package com.example.demo.repository;

import com.example.demo.entity.HealthTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthTipRepository
        extends JpaRepository<HealthTip, Long> {
}