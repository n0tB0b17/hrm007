package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.tenant.TenantCreateRequest;
import com._7.hr.dto.tenant.TenantResponse;
import com._7.hr.dto.tenant.TenantUpdateRequest;
import com._7.hr.exception.ResourceNotFoundException;
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
        return mapTenantToResponse(savedTenant);
    }

    @Transactional(readOnly = true)
    public TenantResponse getTenantByID(String tenantId) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id" + tenantId));

        return mapTenantToResponse(tenant);
    }

    @Transactional(readOnly = true)
    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream()
                .map(this::mapTenantToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TenantResponse updateTenant(String tenantId, TenantUpdateRequest tenantUpdateRequest) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id" + tenantId));

        if (StringUtils.hasText(tenantUpdateRequest.getCompanyName()) &&
                !tenant.getCompanyName().equalsIgnoreCase(tenantUpdateRequest.getCompanyName())) {
            if (tenantRepository.existsByCompanyNameAndTenantIdNot(tenantUpdateRequest.getCompanyName(), tenantId)) {
                throw new TenantAlreadyExistsException("Tenant already exists " + tenantId);
            }
            tenant.setCompanyName(tenantUpdateRequest.getCompanyName());
        }

        if (StringUtils.hasText(tenantUpdateRequest.getCompanyType())) {
            tenant.setCompanyType(tenantUpdateRequest.getCompanyType());
        }
        if (StringUtils.hasText(tenantUpdateRequest.getStatus())) {
            tenant.setStatus(tenantUpdateRequest.getStatus());
        }
        if (StringUtils.hasText(tenantUpdateRequest.getCompanyContactEmail())) {
            tenant.setCompanyContactEmail(tenantUpdateRequest.getCompanyContactEmail());
        }
        if (StringUtils.hasText(tenantUpdateRequest.getCompanyContactNumber())) {
            tenant.setCompanyContactNumber(tenantUpdateRequest.getCompanyContactNumber());
        }
        if (StringUtils.hasText(tenantUpdateRequest.getLogoURL())) {
            tenant.setLogoURL(tenantUpdateRequest.getLogoURL());
        }
        if (StringUtils.hasText(tenantUpdateRequest.getPrimaryColor())) {
            tenant.setPrimaryColor(tenantUpdateRequest.getPrimaryColor());
        }
        if (StringUtils.hasText(tenantUpdateRequest.getSecondaryColor())) {
            tenant.setSecondaryColor(tenantUpdateRequest.getSecondaryColor());
        }

        tenant.setUpdatedAt(LocalDateTime.now());

        Tenant savedTenant = tenantRepository.save(tenant);
        return mapTenantToResponse(savedTenant);
    }

    @Transactional
    public void deleteTenant(String tenantId) {
        if (!tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new ResourceNotFoundException("Tenant not found for given id: " + tenantId);
        }

        tenantRepository.deleteByTenantId(tenantId);
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
