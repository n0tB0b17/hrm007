package com._7.hr.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/tenants")
@Tag(name = "Tenant APIs", description = "this contains all required APIs for tenant related services")
public class TenantController {
    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    @Operation(summary = "Create new tenant", description = "Creates new tenant for any organization interested in HRM analysis")
    @ApiResponse(responseCode = "201 CREATED", description = "Tenant created")
    @ApiResponse(responseCode = "409 CONFLICT", description = "Tenant already exists")
    public ResponseEntity<TenantResponse> registerTenant(@Valid @RequestBody TenantCreateRequest tenantCreateRequest) {
        logger.info("This is tenant create request...");
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
    public ResponseEntity<TenantResponse> deleteTenantById(@PathVariable String tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
