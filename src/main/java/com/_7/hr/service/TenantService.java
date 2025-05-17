package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.tenant.TenantCreateRequest;
import com._7.hr.dto.tenant.TenantResponse;
import com._7.hr.exception.TenantAlreadyExistsException;
import com._7.hr.repository.TenantRepository;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public TenantResponse registerTenant(TenantCreateRequest tenantCreateRequest) {
        if (tenantRepository.existsByCompanyName(tenantCreateRequest.getCompanyName())) {
            throw new TenantAlreadyExistsException(
                    "Tenant already exist with name:> " + tenantCreateRequest.getCompanyName());
        }

        Tenant newTenant = new Tenant();
        newTenant.setTenantId(UUID.randomUUID().toString());
        newTenant.setCompanyName(tenantCreateRequest.getCompanyName());
        newTenant.setCompanyType(tenantCreateRequest.getCompanyType());
        newTenant.setCompanyContactEmail(tenantCreateRequest.getCompanyContactEmail());
        newTenant.setCompanyContactNumber(
                tenantCreateRequest.getCompanyContactNumber() != null ? tenantCreateRequest.getCompanyContactNumber()
                        : "");
        newTenant.setStatus(tenantCreateRequest.getStatus() != null ? tenantCreateRequest.getStatus() : "ACTIVE");
        newTenant.setLogoURL(tenantCreateRequest.getLogoURL());
        newTenant.setPrimaryColor(tenantCreateRequest.getPrimaryColor());
        newTenant.setSecondaryColor(tenantCreateRequest.getSecondaryColor());
        newTenant.setCreatedAt(LocalDateTime.now());
        newTenant.setUpdatedAt(LocalDateTime.now());

        Tenant savedTenant = tenantRepository.save(newTenant);
        // String tenantId = savedTenant.getTenantId();
        return mapTenantToResponse(savedTenant);
    }

    private TenantResponse mapTenantToResponse(Tenant tenant) {
        TenantResponse response = new TenantResponse();

        response.setTenantId(tenant.getTenantId());
        response.setCompanyName(tenant.getCompanyName());
        response.setCompanyType(tenant.getCompanyType());
        response.setCompanyContactEmail(tenant.getCompanyContactEmail());
        response.setCompanyContactNumber(tenant.getCompanyContactNumber());
        response.setStatus(tenant.getStatus());
        response.setLogoURL(tenant.getLogoURL());
        response.setPrimaryColor(tenant.getPrimaryColor());
        response.setSecondaryColor(tenant.getSecondaryColor());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setUpdatedAt(tenant.getUpdatedAt());

        return response;
    }
}
