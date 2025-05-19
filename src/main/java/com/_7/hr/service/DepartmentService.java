package com._7.hr.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._7.hr.dto.department.DepartmentCreateRequest;
import com._7.hr.dto.department.DepartmentUpdateRequest;
import com._7.hr.repository.DepartmentRespoitory;

@Service
public class DepartmentService {
    private final DepartmentRespoitory departmentRespoitory;

    public DepartmentService(DepartmentRespoitory departmentRespoitory) {
        this.departmentRespoitory = departmentRespoitory;
    }

    @Transactional
    public void createDepartment(String tenantId, DepartmentCreateRequest departmentCreateRequest) {
    }

    @Transactional(readOnly = true)
    public void getAllDepartment(String tenantId) {
    }

    @Transactional(readOnly = true)
    public void getDepartmentById(String tenantId, String departmentId) {
    }

    @Transactional
    public void deleteDepartmentById(String tenantId, String departmentId) {
    }

    @Transactional
    public void updateDepartmentById(String tenantId, DepartmentUpdateRequest departmentUpdateRequest) {
    }
}
