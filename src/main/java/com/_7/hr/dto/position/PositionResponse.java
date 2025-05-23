package com._7.hr.dto.position;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PositionResponse {
    private String tenantId;

    private String positionId;
    private String name;
    private String description;
    private boolean isOpen;

    private String roleId;
    private String roleName;

    private String departmentId;
    private String departmentName;

    private String assignedEmployeeId;
    private String assignedEmployeeName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
