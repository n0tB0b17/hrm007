package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._7.hr.domain.role.Role;
import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.role.RoleCreateRequest;
import com._7.hr.dto.role.RoleResponse;
import com._7.hr.dto.role.RoleUpdateRequest;
import com._7.hr.exception.ResourceNotFoundException;
import com._7.hr.exception.RoleAlreadyExistsException;
import com._7.hr.repository.RoleRepository;
import com._7.hr.repository.TenantRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final TenantRepository tenantRepository;

    public RoleService(RoleRepository roleRepository, TenantRepository tenantRepository) {
        this.roleRepository = roleRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public RoleResponse createRole(String tenantId, RoleCreateRequest roleCreateRequest) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));

        if (roleRepository.existsByNameAndTenantId(roleCreateRequest.getName(), tenantId)) {
            throw new RoleAlreadyExistsException(
                    "Role already exist for given name: " + roleCreateRequest.getName() + " for tenant: " + tenantId);
        }

        Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setName(roleCreateRequest.getName());
        role.setDescription(roleCreateRequest.getDescription());
        if (roleCreateRequest.getPermissions() != null) {
            role.setPermissions(new HashSet<>(roleCreateRequest.getPermissions()));
        }

        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        role.setTenant(tenant);

        Role savedRole = roleRepository.save(role);
        return mapToRoleResponse(savedRole);
    }

    @Transactional(readOnly = true)
    public RoleResponse getRoleByIdAndTenantId(String tenantId, String roleId) {
        if (!tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new ResourceNotFoundException("Tenant not found for given id: " + tenantId);
        }

        Role role = roleRepository.findByRoleIdAndTenantId(roleId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role not found for given roleId: " + roleId + " and tenantId: " + tenantId));
        return mapToRoleResponse(role);
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> getRolesByTenantId(String tenantId) {
        List<Role> roles = roleRepository.findAllByTenantId(tenantId);

        return roles.stream()
                .map(this::mapToRoleResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteRoleByIdAndTenantId(String tenantId, String roleId) {
        Role role = roleRepository.findByRoleIdAndTenantId(roleId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role not found for given roleId: " + roleId + " and tenantId: " + tenantId));

        Long isDeleted = roleRepository.deleteByRoleIdAndTenantId(role.getRoleId(), role.getTenant().getTenantId());
        if (isDeleted > 0) {
            return true;
        }

        return false;
    }

    @Transactional
    public RoleResponse updateByRoleIdAndTenantId(String tenantId, String roleId, RoleUpdateRequest roleUpdateRequest) {
        return mapToRoleResponse(null);
    }

    private RoleResponse mapToRoleResponse(Role role) {
        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setRoleId(role.getRoleId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        roleResponse.setCreatedAt(role.getCreatedAt());
        roleResponse.setUpdatedAt(role.getUpdatedAt());

        if (role.getTenant() != null) {
            roleResponse.setTenantId(role.getTenant().getTenantId());
        }

        return roleResponse;
    }
}
