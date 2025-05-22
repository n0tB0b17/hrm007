package com._7.hr.dto.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EmployeeResponse {
    private String tenantId;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private LocalDate hireDate;
    private String departmentId;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
