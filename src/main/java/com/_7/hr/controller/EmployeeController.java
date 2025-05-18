package com._7.hr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._7.hr.dto.employee.EmployeeCreateRequest;
import com._7.hr.dto.employee.EmployeeResponse;
import com._7.hr.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tenants/{tenantId}/employees")
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
}
