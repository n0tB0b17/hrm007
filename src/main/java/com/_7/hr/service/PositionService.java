package com._7.hr.service;

import org.springframework.stereotype.Service;

import com._7.hr.repository.PositionRepository;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

}
