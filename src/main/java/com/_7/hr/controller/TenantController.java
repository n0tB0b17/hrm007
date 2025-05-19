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

import com._7.hr.dto.tenant.TenantCreateRequest;
import com._7.hr.dto.tenant.TenantResponse;
import com._7.hr.dto.tenant.TenantUpdateRequest;
import com._7.hr.service.TenantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse> registerTenant(@Valid @RequestBody TenantCreateRequest tenantCreateRequest) {
        TenantResponse tenantResponse = tenantService.registerTenant(tenantCreateRequest);
        return new ResponseEntity<>(tenantResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable String tenantId) {
        TenantResponse tenantResponse = tenantService.getTenantByID(tenantId);
        return ResponseEntity.ok(tenantResponse);
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        List<TenantResponse> tenantResponses = tenantService.getAllTenants();
        return ResponseEntity.ok(tenantResponses);
    }

    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantResponse> updateTenantById(@PathVariable String tenantId,
            @Valid @RequestBody TenantUpdateRequest tenantUpdateRequest) {
        TenantResponse tenantResponse = tenantService.updateTenant(tenantId, tenantUpdateRequest);
        return ResponseEntity.ok(tenantResponse);
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenantById(@PathVariable String tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
