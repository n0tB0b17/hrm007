package com._7.hr.dto.department;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DepartmentResponse {
    private String tenantId;
    private String departmentId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
