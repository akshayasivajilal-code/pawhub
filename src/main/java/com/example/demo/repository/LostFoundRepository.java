package com.example.demo.repository;

import com.example.demo.entity.LostFound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostFoundRepository extends JpaRepository<LostFound, Long> {
}