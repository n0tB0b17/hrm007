package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com._7.hr.domain.department.Department;
import com._7.hr.domain.position.Position;
import com._7.hr.domain.role.Role;
import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.position.PositionCreateRequest;
import com._7.hr.dto.position.PositionResponse;
import com._7.hr.exception.PositionAlreadyExistsException;
import com._7.hr.exception.ResourceNotFoundException;
import com._7.hr.repository.DepartmentRespoitory;
import com._7.hr.repository.PositionRepository;
import com._7.hr.repository.RoleRepository;
import com._7.hr.repository.TenantRepository;

@Service
public class PositionService {
    private final TenantRepository tenantRepository;
    private final PositionRepository positionRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRespoitory departmentRespoitory;

    public PositionService(TenantRepository tenantRepository, PositionRepository positionRepository,
            RoleRepository roleRepository,
            DepartmentRespoitory departmentRespoitory) {
        this.tenantRepository = tenantRepository;
        this.positionRepository = positionRepository;
        this.roleRepository = roleRepository;
        this.departmentRespoitory = departmentRespoitory;
    }

    @Transactional
    public PositionResponse createRole(String tenantId, PositionCreateRequest positionCreateRequest) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for given Id: " + tenantId));

        if (positionRepository.existsByNameAndTenantId(positionCreateRequest.getName(), tenantId)) {
            throw new PositionAlreadyExistsException("Position exists for given name: "
                    + positionCreateRequest.getName() + " and tenantId: " + tenantId);
        }

        Role role = roleRepository.findByRoleIdAndTenantId(positionCreateRequest.getRoleId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for given roleId: "
                        + positionCreateRequest.getRoleId() + " in tenantId: " + tenantId));

        Department department = null;
        if (StringUtils.hasText(positionCreateRequest.getDepartmentId())) {
            department = departmentRespoitory
                    .findByDepartmentIdAndTenantId(positionCreateRequest.getDepartmentId(), tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found for given department id: "
                            + positionCreateRequest.getDepartmentId() + " inside tenantID: " + tenantId));
        }

        Position position = new Position();
        position.setPositionId(UUID.randomUUID().toString());
        position.setName(positionCreateRequest.getName());
        position.setDescription(positionCreateRequest.getDescription());
        position.setOpen(true);
        position.setRole(role);
        position.setDepartment(department);
        position.setTenant(tenant);
        position.setCreatedAt(LocalDateTime.now());
        position.setUpdatedAt(LocalDateTime.now());

        Position savedPosition = positionRepository.save(position);
        return mapToPositionResponse(savedPosition);
    }

    @Transactional(readOnly = true)
    public List<PositionResponse> getAllPosition(String tenantId) {
        if (!tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new ResourceNotFoundException("Tenant not found for given id: " + tenantId);
        }

        return positionRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::mapToPositionResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PositionResponse getPositionById(String tenantId, String positionId) {
        if (!tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new ResourceNotFoundException("Tenant not found for given id: " + tenantId);
        }

        Position position = positionRepository.findByPositionIdAndTenantId(positionId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Position not found for given positionId: " + positionId + " for tenantId: " + tenantId));

        return mapToPositionResponse(position);
    }

    @Transactional
    public boolean deletePositionById(String tenantId, String positionId) {
        if (!tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new ResourceNotFoundException("Tenant not found for given id: " + tenantId);
        }

        Long deleteResp = positionRepository.deleteByIdAndTenantId(positionId, tenantId);
        if (deleteResp > 0) {
            return true;
        }

        return false;
    }

    // add update

    private PositionResponse mapToPositionResponse(Position position) {
        PositionResponse positionResponse = new PositionResponse();

        positionResponse.setPositionId(position.getPositionId());
        positionResponse.setName(position.getName());
        positionResponse.setDescription(position.getDescription());
        positionResponse.setOpen(position.isOpen());
        positionResponse.setCreatedAt(position.getCreatedAt());
        positionResponse.setUpdatedAt(position.getUpdatedAt());

        if (position.getRole() != null) {
            positionResponse.setRoleId(position.getRole().getRoleId());
            positionResponse.setRoleName(position.getRole().getName());
        }

        if (position.getDepartment() != null) {
            positionResponse.setDepartmentId(position.getDepartment().getDepartmentId());
            positionResponse.setDepartmentName(position.getDepartment().getName());
        }

        if (position.getTenant() != null) {
            positionResponse.setTenantId(position.getTenant().getTenantId());
        }

        return positionResponse;
    }
}
