package com._7.hr.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com._7.hr.domain.employee.Employee;
import com._7.hr.domain.position.Position;
import com._7.hr.domain.tenant.Tenant;
import com._7.hr.dto.employee.EmployeeCreateRequest;
import com._7.hr.dto.employee.EmployeeResponse;
import com._7.hr.dto.employee.EmployeeUpdateRequest;
import com._7.hr.exception.EmployeeAlreadyExistsException;
import com._7.hr.exception.ResourceNotFoundException;
import com._7.hr.repository.DepartmentRespoitory;
import com._7.hr.repository.EmployeeRepository;
import com._7.hr.repository.PositionRepository;
import com._7.hr.repository.TenantRepository;

@Service
public class EmployeeService {
    private final DepartmentRespoitory departmentRespoitory;
    private final EmployeeRepository employeeRepository;
    private final TenantRepository tenantRepository;
    private final PositionRepository positionRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TenantRepository tenantRepository,
            DepartmentRespoitory departmentRespoitory, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.tenantRepository = tenantRepository;
        this.departmentRespoitory = departmentRespoitory;
        this.positionRepository = positionRepository;
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
        newEmployee.setHireDate(employeeCreateRequest.getHireDate());
        newEmployee.setCreatedAt(LocalDateTime.now());
        newEmployee.setUpdatedAt(LocalDateTime.now());
        newEmployee.setTenant(tenant);

        if (StringUtils.hasText(employeeCreateRequest.getPositionId())) {
            Position position = positionRepository
                    .findByPositionIdAndTenantId(employeeCreateRequest.getPositionId(), tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException("position not found for given positionId: "
                            + employeeCreateRequest.getPositionId() + " and tenant id: " + tenantId));

            newEmployee.setPosition(position);
        }

        Employee savedEmployee = employeeRepository.save(newEmployee);
        return this.mapToEmployeeResponse(savedEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployee(String tenantId) {
        if (!tenantRepository.findByTenantId(tenantId).isPresent()) {
            throw new ResourceNotFoundException("Tenant not found for given id: " + tenantId);
        }

        List<EmployeeResponse> employeeResponses = employeeRepository.findByTenantId(tenantId)
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
    public EmployeeResponse updateEmployeeById(String tenantId, String employeeId,
            EmployeeUpdateRequest employeeUpdateRequest) {

        Employee employee = employeeRepository.findByEmployeeIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for given id: " + employeeId));

        if (StringUtils.hasText(employeeUpdateRequest.getFirstName())) {
            employee.setFirstName(employeeUpdateRequest.getFirstName());
        }

        if (StringUtils.hasText(employeeUpdateRequest.getLastName())) {
            employee.setLastName(employeeUpdateRequest.getLastName());
        }

        if (StringUtils.hasText(employeeUpdateRequest.getEmail())) {
            employee.setEmail(employeeUpdateRequest.getEmail());
        }

        // if (StringUtils.hasText(employeeUpdateRequest.getJobTitle())) {
        // employee.setJobTitle(employeeUpdateRequest.getJobTitle());
        // }

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeResponse(savedEmployee);
    }

    @Transactional
    public EmployeeResponse assignDepartment(String tenantId, String employeeId, String departmentId) {
        Employee employee = employeeRepository.findByEmployeeIdAndTenantId(employeeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for given id:" + employeeId + " and tenant id: " + tenantId));

        // if (StringUtils.hasText(departmentId)) {
        // Department department =
        // departmentRespoitory.findByDepartmentIdAndTenantId(departmentId, tenantId)
        // .orElseThrow(() -> new ResourceNotFoundException(
        // "department not found for given id:" + departmentId + " and tenant id: " +
        // tenantId));

        // employee.setDepartment(department);
        // } else {
        // employee.setDepartment(null);
        // }

        employee.setUpdatedAt(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeResponse(savedEmployee);
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();

        employeeResponse.setEmployeeId(employee.getEmployeeId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setHireDate(employee.getHireDate());
        employeeResponse.setCreatedAt(employee.getCreatedAt());
        employeeResponse.setUpdatedAt(employee.getUpdatedAt());

        if (employee.getTenant() != null) {
            employeeResponse.setTenantId(employee.getTenant().getTenantId());
        }

        if (employee.getPosition() != null) {
            Position position = employee.getPosition();
            employeeResponse.setPositionId(position.getPositionId());
            employeeResponse.setPositionName(position.getName());

            if (position.getRole() != null) {
                employeeResponse.setRoleId(position.getRole().getRoleId());
                employeeResponse.setRoleName(position.getRole().getName());
            }

            if (position.getDepartment() != null) {
                employeeResponse.setDepartmentId(position.getDepartment().getDepartmentId());
                employeeResponse.setDepartmentName(position.getDepartment().getName());
            }

        }

        return employeeResponse;
    }

}
