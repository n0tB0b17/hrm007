package com._7.hr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._7.hr.dto.tenant.TenantCreateRequest;
import com._7.hr.dto.tenant.TenantResponse;
import com._7.hr.service.TenantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/tenant")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse> registerTenant(@Valid @RequestBody TenantCreateRequest tenantCreateRequest) {
        TenantResponse tenantResponse = tenantService.registerTenant(tenantCreateRequest);
        return new ResponseEntity<>(tenantResponse, HttpStatus.CREATED);
    }
}
