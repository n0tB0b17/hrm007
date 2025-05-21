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

import com._7.hr.dto.employee.EmployeeCreateRequest;
import com._7.hr.dto.employee.EmployeeResponse;
import com._7.hr.dto.employee.EmployeeUpdateRequest;
import com._7.hr.service.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/employees")
@Tag(name = "Employee APIs", description = "this contains all required APIs for tenant's employee related services")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@PathVariable String tenantId,
            @Valid @RequestBody EmployeeCreateRequest employeeCreateRequest) {
        EmployeeResponse employeeResponse = employeeService.createEmployee(tenantId, employeeCreateRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployeesFromTenant(@PathVariable String tenantId) {
        List<EmployeeResponse> employees = employeeService.getAllEmployee(tenantId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String tenantId,
            @PathVariable String employeeId) {
        EmployeeResponse employee = employeeService.getEmployeeByID(tenantId, employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> deleteEmployee(@PathVariable String tenantId,
            @PathVariable String employeeId) {
        employeeService.deleteEmployeeByID(tenantId, employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String tenantId,
            @PathVariable String employeeId, @Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        EmployeeResponse employeeResponse = employeeService.updateEmployeeById(tenantId, employeeId,
                employeeUpdateRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }
}
