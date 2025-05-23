package com._7.hr.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._7.hr.dto.position.PositionCreateRequest;
import com._7.hr.dto.position.PositionResponse;
import com._7.hr.dto.position.PositionUpdateRequest;
import com._7.hr.service.PositionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/positions")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(@PathVariable String tenantId,
            @RequestBody @Valid PositionCreateRequest positionCreateRequest) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getPositions(@PathVariable String tenantId) {
        return null;
    }

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponse> getPositionById(@PathVariable String tenantId,
            @PathVariable String positionId) {
        return null;
    }

    @PutMapping("/{positionId}")
    public ResponseEntity<PositionResponse> updatePosition(@PathVariable String tenantId,
            @PathVariable String positionId, @RequestBody @Valid PositionUpdateRequest positionUpdateRequest) {
        return null;
    }
}
