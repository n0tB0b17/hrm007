package com._7.hr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com._7.hr.dto.department.DepartmentCreateRequest;
import com._7.hr.dto.department.DepartmentResponse;
import com._7.hr.dto.department.DepartmentUpdateRequest;
import com._7.hr.service.DepartmentService;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/departments")
@Tag(name = "Departments APIs", description = "this contains all required APIs for tenant's department related services")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @PathVariable String tenantId,
            @Valid @RequestBody DepartmentCreateRequest departmentCreateRequest) {

        DepartmentResponse departmentResponse = departmentService.createDepartment(tenantId, departmentCreateRequest);
        return new ResponseEntity<>(departmentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable String tenantId,
            @PathVariable String departmentId) {
        DepartmentResponse departmentResponse = departmentService.getDepartmentById(tenantId, departmentId);
        return new ResponseEntity<>(departmentResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments(@PathVariable String tenantId) {
        List<DepartmentResponse> departmentResponses = departmentService.getAllDepartment(tenantId);
        return new ResponseEntity<>(departmentResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> deleteDepartment(@PathVariable String tenantId,
            @PathVariable String departmentId) {
        boolean deleteResponse = departmentService.deleteDepartmentById(tenantId, departmentId);
        if (!deleteResponse) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable String tenantId,
            @PathVariable String departmentId, @Valid @RequestBody DepartmentUpdateRequest departmentUpdateRequest) {
        DepartmentResponse departmentResponse = departmentService.updateDepartmentById(tenantId, departmentId,
                departmentUpdateRequest);

        return new ResponseEntity<>(departmentResponse, HttpStatus.CREATED);
    }
}
