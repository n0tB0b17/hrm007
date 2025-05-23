package com._7.hr.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/positions")
@Tag(name = "Position APIs", description = "this contains all required APIs for tenant's position related services")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(@PathVariable String tenantId,
            @RequestBody @Valid PositionCreateRequest positionCreateRequest) {
        PositionResponse positionResponse = positionService.createRole(tenantId, positionCreateRequest);
        return new ResponseEntity<>(positionResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getPositions(@PathVariable String tenantId) {
        List<PositionResponse> positionResponses = positionService.getAllPosition(tenantId);
        return new ResponseEntity<>(positionResponses, HttpStatus.OK);
    }

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponse> getPositionById(@PathVariable String tenantId,
            @PathVariable String positionId) {
        PositionResponse positionResponse = positionService.getPositionById(tenantId, positionId);
        return new ResponseEntity<>(positionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{positionId}")
    public ResponseEntity<Boolean> deletePositionById(@PathVariable String tenantId, @PathVariable String positionId) {
        boolean deleteResp = positionService.deletePositionById(tenantId, positionId);
        return new ResponseEntity<>(deleteResp, HttpStatus.OK);
    }

    @PutMapping("/{positionId}")
    public ResponseEntity<PositionResponse> updatePosition(@PathVariable String tenantId,
            @PathVariable String positionId, @RequestBody @Valid PositionUpdateRequest positionUpdateRequest) {
        return null;
    }
}
