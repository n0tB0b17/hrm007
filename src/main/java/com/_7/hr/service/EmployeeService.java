package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._7.hr.domain.employee.Employee;
import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.employee.EmployeeCreateRequest;
import com._7.hr.dto.employee.EmployeeResponse;
import com._7.hr.exception.EmployeeAlreadyExistsException;
import com._7.hr.exception.ResourceNotFoundException;
import com._7.hr.repository.EmployeeRepository;
import com._7.hr.repository.TenantRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TenantRepository tenantRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TenantRepository tenantRepository) {
        this.employeeRepository = employeeRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public EmployeeResponse createEmployee(String tenantId, EmployeeCreateRequest employeeCreateRequest) {
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));

        if (employeeRepository.existsByEmailAndTenantId(employeeCreateRequest.getEmail(), tenantId)) {
            throw new EmployeeAlreadyExistsException(
                    "Employee with email: " + employeeCreateRequest.getEmail() + "already exist for this tenant: "
                            + tenantId);
        }

        Employee newEmployee = new Employee();
        newEmployee.setEmployeeId(UUID.randomUUID().toString());
        newEmployee.setFirstName(employeeCreateRequest.getFirstName());
        newEmployee.setLastName(employeeCreateRequest.getLastName());
        newEmployee.setEmail(employeeCreateRequest.getEmail());
        newEmployee.setJobTitle(employeeCreateRequest.getJobTitle());
        newEmployee.setHireDate(employeeCreateRequest.getHireDate());
        newEmployee.setCreatedAt(LocalDateTime.now());
        newEmployee.setUpdatedAt(LocalDateTime.now());
        newEmployee.setTenant(tenant);

        Employee savedEmployee = employeeRepository.save(newEmployee);
        return this.mapToEmployeeResponse(savedEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployee(String tenantId) {
        List<EmployeeResponse> employeeResponses = employeeRepository.findAllByTenantId(tenantId)
                .stream().map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
        return employeeResponses;
    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeByID(String tenantId, String employeeId) {
        Employee employee = employeeRepository.findByEmployeeIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for given id: " + employeeId));

        return mapToEmployeeResponse(employee);
    }

    @Transactional
    public void deleteEmployeeByID(String tenantId, String employeeId) {
        employeeRepository.deleteByEmployeeIdAndTenantId(employeeId, tenantId);
    }

    @Transactional
    public EmployeeResponse updateEmployeeById(String tenantId, String employeeId) {
        return null;
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setJobTitle(employee.getJobTitle());
        employeeResponse.setHireDate(employee.getHireDate());
        employeeResponse.setCreatedAt(employee.getCreatedAt());
        employeeResponse.setUpdatedAt(employee.getUpdatedAt());

        if (employee.getTenant() != null) {
            employeeResponse.setTenantId(employee.getTenant().getTenantId());
        }

        return employeeResponse;
    }
}
