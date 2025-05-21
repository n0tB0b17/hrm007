package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com._7.hr.domain.department.Department;
import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.department.DepartmentCreateRequest;
import com._7.hr.dto.department.DepartmentResponse;
import com._7.hr.dto.department.DepartmentUpdateRequest;
import com._7.hr.exception.DepartmentAlreadyExistsException;
import com._7.hr.exception.ResourceNotFoundException;
import com._7.hr.repository.DepartmentRespoitory;
import com._7.hr.repository.TenantRepository;

@Service
public class DepartmentService {
    private final TenantRepository tenantRepository;
    private final DepartmentRespoitory departmentRespoitory;

    public DepartmentService(TenantRepository tenantRepository, DepartmentRespoitory departmentRespoitory) {
        this.tenantRepository = tenantRepository;
        this.departmentRespoitory = departmentRespoitory;
    }

    @Transactional
    public DepartmentResponse createDepartment(String tenantId, DepartmentCreateRequest departmentCreateRequest) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for given id: " + tenantId));

        if (departmentRespoitory.existsByNameAndTenantId(departmentCreateRequest.getName(), tenantId)) {
            throw new DepartmentAlreadyExistsException(
                    "Department name: " + departmentCreateRequest.getName() + "already exist on tenant: " + tenantId);
        }

        Department department = new Department();
        department.setDepartmentId(UUID.randomUUID().toString());
        department.setName(departmentCreateRequest.getName());
        if (StringUtils.hasText(departmentCreateRequest.getDescription())) {
            department.setDescription(departmentCreateRequest.getDescription());
        }

        department.setCreatedAt(LocalDateTime.now());
        department.setUpdatedAt(LocalDateTime.now());
        department.setTenant(tenant);

        Department savedDepartment = departmentRespoitory.save(department);
        return mapToDepartmentResponse(savedDepartment);
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartment(String tenantId) {
        List<Department> departments = departmentRespoitory.findAllByTenantId(tenantId);

        return departments.stream()
                .map(this::mapToDepartmentResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(String tenantId, String departmentId) {
        Department department = departmentRespoitory.findByDepartmentIdAndTenantId(departmentId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("department not found for given id: " + departmentId));

        return mapToDepartmentResponse(department);
    }

    @Transactional
    public boolean deleteDepartmentById(String tenantId, String departmentId) {
        Long deleteResponse = departmentRespoitory.deleteByDepartmentIdAndTenantId(departmentId, tenantId);
        if (deleteResponse != 0) {
            return true;
        }

        return false;
    }

    @Transactional
    public void updateDepartmentById(String tenantId, DepartmentUpdateRequest departmentUpdateRequest) {
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();

        departmentResponse.setDepartmentId(department.getDepartmentId());
        departmentResponse.setName(department.getName());
        departmentResponse.setDescription(department.getDescription());
        departmentResponse.setCreatedAt(department.getCreatedAt());
        departmentResponse.setUpdatedAt(department.getUpdatedAt());

        if (department.getTenant() != null) {
            departmentResponse.setTenantId(department.getTenant().getTenantId());
        }

        return departmentResponse;
    }
}
