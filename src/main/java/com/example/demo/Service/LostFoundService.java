package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.LostFound;
import com.example.demo.repository.LostFoundRepository;

@Service
public class LostFoundService {

    private final LostFoundRepository lostFoundRepository;

    public LostFoundService(LostFoundRepository lostFoundRepository) {
        this.lostFoundRepository = lostFoundRepository;
    }

    public LostFound saveReport(LostFound lostFound) {
        return lostFoundRepository.save(lostFound);
    }

    public List<LostFound> getAllReports() {
        return lostFoundRepository.findAll();
    }
}